package pub.makers.shop.logistics.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.lantu.base.common.entity.BoolType;

import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightShipping;
import pub.makers.shop.logistics.entity.FreightShippingItem;
import pub.makers.shop.logistics.service.FreightPricingService;
import pub.makers.shop.logistics.service.FreightShippingService;
import pub.makers.shop.logistics.utils.RegionUtils;
import pub.makers.shop.logistics.vo.FreightVo;
import pub.makers.shop.tradeOrder.vo.IndentListVo;

/**
 * 计件运费计算服务
 * @author apple
 *
 */
@Service
public class PieceFreightPricingServiceImpl implements FreightPricingService{

	@Autowired
	private FreightShippingService shippingService;
	
	public List<FreightVo> calcFreight(List<FreightShipping> shippingList, IndentListVo ivo, TradeContext tc) {
		
		List<FreightVo> fvoList = Lists.newArrayList();
		
		// 根据交易上下文查询此模板匹配
		for (FreightShipping shipping : shippingList){
			
			FreightVo fvo = new FreightVo();
			
			FreightShippingItem item = getValidateItem(shipping.getItemList(), tc);
			
			int totalNum = ivo.getNumber();
			// 增加数量
			int incrNum = totalNum - item.getFirstNum().intValue();
			if (incrNum < 0 ){ incrNum = 0; }
			// 计算倍数
			Double incrBs = incrNum / item.getIncrNum().doubleValue();
			// 倍数向上取整
			int bs = new BigDecimal(incrBs).setScale(0, RoundingMode.DOWN).intValue();
			BigDecimal firstFreight = item.getFirstPrice();
			BigDecimal incrFreight = item.getIncrPrice().multiply(new BigDecimal(bs));
			
			ValidateUtils.notNull(shipping.getServicerId(), String.format("运费模板{%s}配置错误", shipping.getTplId()));
			fvo.addIndentListVo(ivo);
			fvo.setFreeFalg(false);
			fvo.setFirstFreight(firstFreight);
			fvo.setIncrFreight(incrFreight);
			fvo.setMethodId(shipping.getMethodId());
			fvo.setMethodName(shipping.getMethodName());
			fvo.setServicerId(shipping.getServicerId());
			fvo.setServicerName(shipping.getServicerName());
			fvo.setTotalFreight(fvo.getFirstFreight().add(fvo.getIncrFreight()));
			
			fvoList.add(fvo);
		}
		
		return fvoList;
	}
	
	private FreightShippingItem getValidateItem(List<FreightShippingItem> itemList, TradeContext tc) {
		
		// 查询列表
		// 根据地址匹配
		FreightShippingItem defaultItem = null;
		FreightShippingItem matchItem = null;
		
		for (FreightShippingItem item : itemList){
			
			if (BoolType.T.name().equals(item.getDefaultFlag())){
				defaultItem = item;
				continue;
			}
			
			// 当匹配到，就停止计算
			if (RegionUtils.regionMatch(item.getAddrIds(), tc.getRegionId())){
				matchItem = item;
				break;
			}
		}
		
		return matchItem  == null ? defaultItem : matchItem;
	}

}
