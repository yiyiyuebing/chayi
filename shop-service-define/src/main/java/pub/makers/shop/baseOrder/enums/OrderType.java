package pub.makers.shop.baseOrder.enums;

/**
 * 订单业务类型
 * 
 * @author apple
 *
 */
public enum OrderType {

	normal("普通订单"), presell("预售订单"), coupon("兑换券订单");
	private String name;

	private OrderType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static OrderType fromName(String name) {

		OrderType result = null;
		for (OrderType bizType : OrderType.values()) {
			if (bizType.name().equals(name)) {
				result = bizType;
				break;
			}
		}

		return result;
	}
}
