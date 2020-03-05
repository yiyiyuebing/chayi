package pub.makers.shop.baseOrder.enums;

/**
 * 订单状态枚举
 * @author zhuzd
 *
 */
public enum OrderStatus {
	confirm("待确认",0),
	pay("待付款",1),ship("待发货",2),refund("退款申请",3),receive("待收货",5),return1("退货申请",6),payend("待支付尾款", 20),
	returning("退货中",7),refunded("已退款",8),returned("已退货",9),received("已收货",10),cancel("已取消",11),
	evaluate("待评价",12),done("已完成",13),refunding("退款中",14);
	
	private String name;
    private int dbData;
    
    
    private OrderStatus(String name,int dbData) {
        this.name = name;
        this.dbData = dbData;
    }
    
    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public int getDbData() {
		return dbData;
	}

	public void setDbData(int dbData) {
		this.dbData = dbData;
	}

	@Override
    public String toString() {
        return this.dbData+"";
    }
	
	public static int getDbDataByName(String name) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getDbDataByName(Integer status) {
		for (OrderStatus c : OrderStatus.values()) {
			if (c.getDbData() == status) {
				return c.name;
			}
		}
		return "";
	}

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (OrderStatus c : OrderStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static OrderStatus getStatusByDbData(Integer status) {
		if(status != null){
			for (OrderStatus c : OrderStatus.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
