package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.entity.WeixinUserInfoVo;
import pub.makers.shop.user.utils.WxUserUtils;

@Service
public class WeixinUserAppService {
	@Reference(version = "1.0.0")
	private WeixinUserInfoBizService userBizService;
	
	public WeixinUserInfo weixinLogin(WeixinUserInfoVo userInfo, Long storeId, String openId){
		
		WeixinUserInfo dbUser = userBizService.addOrUpdateUserInfo(openId, storeId, userInfo);
		String token = WxUserUtils.setCurrUser(dbUser);
		dbUser.setToken(token);
		
		return dbUser;
	}
}
