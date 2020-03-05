package pub.makers.shop.user.controller;

import com.alibaba.druid.util.Base64;
import com.dev.base.json.JsonUtils;
import com.dev.base.utils.HttpClientUtils;
import com.lantu.base.constant.CfgConstants;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.common.utils.AESUtil;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.entity.WeixinUserInfoVo;
import pub.makers.shop.user.pojo.WeixinOauth2Token;
import pub.makers.shop.user.service.WeixinUserAppService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("wx/login")
public class WxLoginController {

	@Autowired
	private WeixinUserAppService wxUserAppService;
	private Map<String, String> properties = CfgConstants.getProperties();
	private String appId = properties.get("wx.appid");
	private String appsecret = properties.get("wx.appsecret");

	@RequestMapping("auth")
	public String auth(String param){

		String callbackUrl = properties.get("wx.logincallback") + "?param=" + param;
		String wxLoginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		String redirectUrl = String.format(wxLoginUrl, appId, callbackUrl);

		return "redirect:" + redirectUrl;
	}

	@RequestMapping("callback")
	public String callback(HttpServletRequest request, HttpServletResponse response, String code, String param)
			throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {

		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="
				+ appsecret + "&code=" + code + "&grant_type=authorization_code";
		String tokenStr = HttpClientUtils.getHttps(tokenUrl);
        System.out.println(String.format("微信code：%s", code));
        System.out.println(String.format("微信tokenStr：%s", tokenStr));
        WeixinOauth2Token token = JsonUtils.toObject(tokenStr, WeixinOauth2Token.class);

		// 网页授权接口访问凭证
		String accessToken = token.getAccess_token();
		// 用户标识
		String openId = token.getOpenid();

		request.getSession().setAttribute("wxopenId", openId);

		String paramJson = new String(Base64.base64ToByteArray(param));
		paramJson = paramJson.substring(paramJson.indexOf("{"), paramJson.lastIndexOf("}") + 1);
		Map<String, Object> paramMap = JsonUtils.toObject(paramJson, Map.class);
		System.out.println(paramJson);
        String storeId = "";
        try{
        	storeId = ((List) paramMap.get("storeId")).get(0).toString();
        } catch (Exception e){  }
        
        String id = "";
        try{
        	id = ((List) paramMap.get("id")).get(0).toString();
        } catch (Exception e){  }
        
        String redirectUri = paramMap.get("uri").toString();

		String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        System.out.println(String.format("微信accessToken：%s", accessToken));
        System.out.println(String.format("微信openId：%s", openId));
        String userStr = HttpClientUtils.getHttps(userUrl);
		System.out.println(String.format("微信授权回调：%s", userStr));

		WeixinUserInfoVo userInfo = JsonUtils.toObject(userStr, WeixinUserInfoVo.class);
		WeixinUserInfo dbUser = wxUserAppService.weixinLogin(userInfo, Long.valueOf(storeId), openId);
		request.getSession().setAttribute("userId", dbUser.getID());
		Cookie c = new Cookie("b2c_token", dbUser.getToken());
		c.setDomain("youchalian.com");
		c.setSecure(false);
		c.setPath("/");
		c.setMaxAge(-1);

		response.addCookie(c);
		System.out.println(String.format("微信授权回调：%s", dbUser));
		return String.format("redirect:%s?storeId=%s&userId=%s&id=%s", redirectUri, storeId, dbUser.getID() + "", id);
	}


	@RequestMapping("decodeParam")
	@ResponseBody
	public String decodeParam(String param) throws Exception{
		String json = AESUtil.aesDecrypt(param);

		return json;
	}
}
