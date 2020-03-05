package pub.makers.shop.baseOrder.pojo;

import pub.makers.shop.baseGood.pojo.BaseGood;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderType;

import java.io.Serializable;
import java.util.List;

/**
 * 商品促销查询
 * @author apple
 *
 */
public class PromotionGoodQuery implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2606754532326996159L;

	private List<BaseGood> goodList;
	
	private OrderBizType orderBizType;
	
	private OrderType orderType;

	private Boolean isDetail;

	
	public List<BaseGood> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<BaseGood> goodList) {
		this.goodList = goodList;
	}

	public OrderBizType getOrderBizType() {
		return orderBizType;
	}

	public void setOrderBizType(OrderBizType orderBizType) {
		this.orderBizType = orderBizType;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Boolean getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(Boolean isDetail) {
		this.isDetail = isDetail;
	}
}
