package pub.makers.shop.baseOrder.enums;


/**
 * 订单业务类型
 * @author apple
 *
 */
public enum OrderBizType {

	trade("商城订单"), purchase("采购订单");
	private String name;
    
    
    private OrderBizType(String name) {
        this.name = name;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public static OrderBizType fromName(String name){
		
		OrderBizType result = null;
		for (OrderBizType bizType : OrderBizType.values()){
			if (bizType.name().equals(name)){
				result = bizType;
				break;
			}
		}
		
		return result;
	}
}
