package pub.makers.shop.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.user.dao.WeixinUserInfoDao;
import pub.makers.shop.user.entity.WeixinUserInfo;
import pub.makers.shop.user.service.WeixinUserInfoService;

import java.util.List;

/**
 * Created by dy on 2017/5/5.
 */
@Service
public class WeixinUserInfoServiceImpl extends BaseCRUDServiceImpl<WeixinUserInfo, String, WeixinUserInfoDao> implements WeixinUserInfoService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public WeixinUserInfo findByOpenId(String openId) {
		List<WeixinUserInfo> infoList = jdbcTemplate.query(String.format("select * from weixin_user_info where 1 = 1  and openId = '%s'", openId), ParameterizedBeanPropertyRowMapper.newInstance(WeixinUserInfo.class));
		if (infoList.isEmpty()) {
			return null;
		} else {
			return infoList.get(0);
		}
	}
}
