package pub.makers.shop.logistics.vo;

import java.io.Serializable;
import java.util.List;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.pojo.TradeContext;

public class FreightTplQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -105594070779214163L;
	
	private List<FreightTplQueryItem> itemList;
	private OrderBizType orderBizType;
	private TradeContext tradeContext;
	private String serviceId;
	
	public List<FreightTplQueryItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<FreightTplQueryItem> itemList) {
		this.itemList = itemList;
	}
	public OrderBizType getOrderBizType() {
		return orderBizType;
	}
	public void setOrderBizType(OrderBizType orderBizType) {
		this.orderBizType = orderBizType;
	}
	public TradeContext getTradeContext() {
		return tradeContext;
	}
	public void setTradeContext(TradeContext tradeContext) {
		this.tradeContext = tradeContext;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
}
