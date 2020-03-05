package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.store.service.SubbranchBizService;
import pub.makers.shop.tradeGoods.service.TradeGoodBizService;

import java.util.Map;


@Service
public class CustomServiceAppService {
	
	@Reference(version="1.0.0")
	private WeixinUserInfoBizService weixinUserInfoBizService;
	@Reference(version="1.0.0")
	private SubbranchBizService subbranchBizService;
	@Reference(version="1.0.0")
	private TradeGoodBizService tradeGoodBizService;

	
	public Map<String,Object> getContext(String goodId ,Long userId ,String shopId){
		
		Map<String, Object> info = Maps.newHashMap();
		if (StringUtils.isNotEmpty(goodId)) {
			info.put("good", tradeGoodBizService.getGoodVoById(goodId, ClientType.mobile));
		}
		if (userId != null) {
			info.put("user", weixinUserInfoBizService.getWxUserById(userId));
		}
		if (StringUtils.isNotEmpty(shopId)) {
			info.put("shop", subbranchBizService.getById(shopId));
		}
		return info;
	}
	
	
}
