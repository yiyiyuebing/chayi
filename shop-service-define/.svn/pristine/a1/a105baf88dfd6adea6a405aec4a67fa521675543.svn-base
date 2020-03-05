package pub.makers.shop.baseOrder.pojo;

import pub.makers.shop.baseOrder.vo.OrderPresellExtraVo;
import pub.makers.shop.invoice.vo.InvoiceVo;
import pub.makers.shop.promotion.vo.PromotionActivityVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public abstract class BaseOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8108368634533163533L;

	
	public abstract List<? extends BaseOrderItem> getItemList();

	public abstract void setItemList(List<? extends BaseOrderItem> itemList);
	
	/**
	 * 获取商品总额
	 * @return
	 */
	public abstract BigDecimal getTotalPrice();
	
	/**
	 * 设置商品总额
	 * @param price
	 */
	public abstract void setTotalPrice(BigDecimal price);
	
	/**
	 * 获取商品运费
	 * @return
	 */
	public abstract BigDecimal getFreight();
	
	/**
	 * 设置商品运费
	 * @param price
	 */
	public abstract void setFreight(BigDecimal price);
	
	/**
	 * 获取待支付金额
	 * 待支付金额=总金额+运费-优惠
	 * @return
	 */
	public abstract BigDecimal getWaitpayAmount();
	
	/**
	 * 设置待支付金额
	 * 待支付金额=总金额+运费-优惠
	 * @param waitpayAmount
	 */
	public abstract void setWaitpayAmount(BigDecimal waitpayAmount);
	
	
	/**
	 * 设置订单总优惠金额
	 * @param discountAmount
	 */
	public abstract void setDiscountAmount(BigDecimal discountAmount);
	
	/**
	 * 获取订单总优惠金额
	 * @return
	 */
	public abstract BigDecimal getDiscountAmount();
	
	/**
	 * 设置订单的实际支付金额
	 */
	public abstract void setPaymentAmount(BigDecimal amount);
	
	/**
	 * 获取订单的实际支付金额
	 * @return
	 */
	public abstract BigDecimal getPaymentAmount();
	
	/**
	 * 获取支付方式
	 * @return
	 */
	public abstract String getPayway();
	
	/**
	 * 设置支付方式
	 * @param payway
	 */
	public abstract void setPayway(String payway);
	
	/**
	 * 获取支付时间
	 * @return
	 */
	public abstract Date getPaytime();
	
	/**
	 * 设置支付时间
	 * @param paytime
	 */
	public abstract void setPaytime(Date paytime);

	/**
	 * 是否已退运费
	 * @return
	 */
	public abstract String getIsReturnCarriage();
	
	
	/**
	 * 获取当前交易的上下文
	 * @return
	 */
	public abstract TradeContext getTradeContext();
	
	public abstract void setOrderId(String orderId);

	public abstract String getOrderId();
	
	public abstract void setOrderCode(String orderCode); 
	
	public abstract void setNumber(Integer num);
	
	public abstract void setStatus(Integer status);
	
	public abstract Integer getStatus();
	
	public abstract String getBuyerId();

	public abstract Serializable getId();
	
	public abstract Date getCreateTime();
	
	public abstract void setCreateTime(Date d);
	
	public abstract Date getTimeout();

	public abstract void setTimeout(Date timeout);
	
	public abstract String getOrderType();

	public abstract void setOrderType(String orderType);
	
	public abstract String getBuyerDelFlag();

	public abstract void setBuyerDelFlag(String buyerDelFlag);
	
	public abstract String getSellerDelFlag();

	public abstract void setSellerDelFlag(String sellerDelFlag);

	public abstract void setPresellExtra(OrderPresellExtraVo presellExtra);

	public abstract void setActivityList(List<PromotionActivityVo> activityList);

	public abstract InvoiceVo getInvoiceVo();
}
