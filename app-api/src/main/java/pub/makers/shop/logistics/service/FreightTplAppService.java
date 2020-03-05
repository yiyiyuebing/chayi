package pub.makers.shop.logistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import pub.makers.shop.baseOrder.pojo.TradeContext;
import pub.makers.shop.logistics.entity.FreightTpl;
import pub.makers.shop.logistics.vo.FreightResultVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderListVo;
import pub.makers.shop.purchaseOrder.vo.PurchaseOrderVo;
import pub.makers.shop.tradeOrder.vo.IndentVo;

@Service
public class FreightTplAppService {

	@Reference(version="1.0.0")
	private FreightTplBizService tplBizService;
	
	public FreightResultVo showTradeOptions(IndentVo indent, TradeContext context){
		
		return tplBizService.queryTradeOrderOptions(indent, context);
	}
	
	public FreightResultVo showPurchaseOptions(List<PurchaseOrderListVo> orderList, TradeContext context){
		
		PurchaseOrderVo order = new PurchaseOrderVo();
		order.setOrderListVos(orderList);
		
		return tplBizService.queryPurchaseOrderOptions(order, context);
	}
	
	public FreightTpl queryPrimaryTpl(String skuId, TradeContext context, String relType){
		
		return tplBizService.queryPrimaryTpl(skuId, context, relType);
	}
}
