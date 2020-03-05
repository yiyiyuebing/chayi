package pub.makers.shop.tradeOrder.enums;

/**
 * 订单交易状态枚举
 * @author zhuzd
 *
 */
public enum IndentDealStatus {

	deal_close("交易关闭",0),deal_fail("交易失败",1),deal_success("交易成功",2);
	
	private String name;
    private int dbData;
    
    
    private IndentDealStatus(String name, int dbData) {
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
        for (IndentDealStatus c : IndentDealStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

	public static String getTextByDbData(Integer status) {
		String result = "";
		if(status != null){
			for (IndentDealStatus c : IndentDealStatus.values()) {
	            if (c.getDbData() == status) {
	                return c.name();
	            }
	        }
		}
		return result;
	}
	
	public static IndentDealStatus getStatusByDbData(Integer status) {
		if(status != null){
			for (IndentDealStatus c : IndentDealStatus.values()) {
	            if (c.getDbData() == status) {
	                return c;
	            }
	        }
		}
		return null;
	}
}
