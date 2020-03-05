package pub.makers.shop.user.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.base.json.JsonUtils;
import com.dev.base.utils.HttpClientUtils;
import com.lantu.base.pay.ali.util.Base64;
import com.lantu.base.util.PropertiesUtilsEx;

import pub.makers.shop.user.pojo.WeixinOauth2Token2Ch;
import pub.makers.shop.user.utils.AccountUtils;

@Controller
@RequestMapping("wx/login")
public class WxB2BLoginController {

	private static final Map<String, String> PROPERTIES = PropertiesUtilsEx.getProperties("weixin.properties");
	private String appId = PROPERTIES.get("pur.appId");
	private String appsecret = PROPERTIES.get("pur.appsecret");
	
	@RequestMapping("auth")
	public String auth(String param){
		
		
		String callbackUrl = PROPERTIES.get("weixin.login.callback") + "?param=" + param;
		String wxLoginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		String redirectUrl = String.format(wxLoginUrl, appId, callbackUrl);
		
		return "redirect:" + redirectUrl;
	}
	
	
	@RequestMapping("callback")
	public String callback(HttpServletRequest request, String code, String param) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException{
	
		String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="
				+ appsecret + "&code=" + code + "&grant_type=authorization_code";
		String tokenStr = HttpClientUtils.getHttps(tokenUrl);
		
		WeixinOauth2Token2Ch token = JsonUtils.toObject(tokenStr, WeixinOauth2Token2Ch.class);
		
		// 网页授权接口访问凭证
		String accessToken = token.getAccess_token();
		// 用户标识
		String openId = token.getOpenid();
		// 保存当前用户OPENID
		AccountUtils.setOpenId(openId);
		
		String redirectUrl = new String(Base64.decode(param)) + "&openId=" + openId;
		System.out.println(redirectUrl);
		
		return "redirect:" + redirectUrl;
	}
}
