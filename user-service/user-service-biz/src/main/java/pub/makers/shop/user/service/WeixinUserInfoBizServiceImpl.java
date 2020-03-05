package pub.makers.shop.user.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.config.annotation.Service;

import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.entity.WeixinUserInfoVo;

/**
 * Created by dy on 2017/5/5.
 */
@Service(version = "1.0.0")
public class WeixinUserInfoBizServiceImpl implements WeixinUserInfoBizService {

    @Autowired
    private WeixinUserInfoService weixinUserInfoService;
    @Autowired
    private WeixinStoreWeixinuserService storeUserService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public WeixinUserInfo getWxUserById(Long userId) {
    	
        return weixinUserInfoService.getById(userId);
    }

	@Override
	public WeixinUserInfo addOrUpdateUserInfo(String openId, Long storeId, WeixinUserInfoVo userInfo) {
		
		WeixinUserInfo dbUser = weixinUserInfoService.findByOpenId(openId);
		if (dbUser != null){
			
			dbUser.setSubscribe(userInfo.getSubscribe());
			dbUser.setSubscribeTime(userInfo.getSubscribetime());
			dbUser.setNickname(userInfo.getNickname());
			dbUser.setSex(userInfo.getSex());
			dbUser.setCountry(userInfo.getCountry());
			dbUser.setProvince(userInfo.getProvince());
			dbUser.setCity(userInfo.getCity());
			dbUser.setLanguage(userInfo.getLanguage());
			dbUser.setHeadImgUrl(userInfo.getHeadimgurl());
			dbUser.setLastLoginTime(new Date());
			
			weixinUserInfoService.update(dbUser);
		}
		else {
			dbUser = new WeixinUserInfo();
			dbUser.setID(IdGenerator.getDefault().nextId());
			dbUser.setOpenId(openId);
			dbUser.setSubscribe(userInfo.getSubscribe());
			dbUser.setSubscribeTime(userInfo.getSubscribetime());
			dbUser.setNickname(userInfo.getNickname());
			dbUser.setSex(userInfo.getSex());
			dbUser.setCountry(userInfo.getCountry());
			dbUser.setProvince(userInfo.getProvince());
			dbUser.setCity(userInfo.getCity());
			dbUser.setLanguage(userInfo.getLanguage());
			dbUser.setHeadImgUrl(userInfo.getHeadimgurl());
			dbUser.setLastLoginTime(new Date());
			dbUser.setCreateTime(new Date());
			
			jdbcTemplate.update("INSERT INTO `weixin_user_info` "
					+ "(`ID`, `openId`, `subscribe`, `subscribeTime`, `nickname`, `sex`, `country`, `province`, `city`, `language`, `headImgUrl`, `last_login_time`,`create_time`) values "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
					IdGenerator.getDefault().nextId(), openId, userInfo.getSubscribe(), userInfo.getSubscribetime(), userInfo.getNickname(), userInfo.getSex(), userInfo.getCountry(), userInfo.getProvince(), userInfo.getCity(),
					userInfo.getLanguage(), userInfo.getHeadimgurl(), new Date(), new Date());
		}
		
		storeUserService.addStoreUser(storeId, dbUser.getID(), IdGenerator.getDefault().nextId());
		
		return dbUser;
	}
}
