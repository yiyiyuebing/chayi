package pub.makers.shop.bill.enums;

/**
 * Created by dy on 2017/9/6.
 */
public enum OrderBillStatus {
    wait("待结算", 1), unpay("已结算待付款", 2), payed("已结算已付款", 3), unbill("未结算", 0);

    private String name;
    private int dbData;


    private OrderBillStatus(String name, int dbData) {
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


    public static int getDbDataByName(String name) {
        for (OrderBillStatus c : OrderBillStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

    public static String getDbDataByName(Integer status) {
        for (OrderBillStatus c : OrderBillStatus.values()) {
            if (c.getDbData() == status) {
                return c.name;
            }
        }
        return "";
    }

    public static String getTextByDbData(Integer status) {
        String result = "";
        if(status != null){
            for (OrderBillStatus c : OrderBillStatus.values()) {
                if (c.getDbData() == status) {
                    return c.name();
                }
            }
        }
        return result;
    }

    public static OrderBillStatus getStatusByDbData(Integer status) {
        if(status != null){
            for (OrderBillStatus c : OrderBillStatus.values()) {
                if (c.getDbData() == status) {
                    return c;
                }
            }
        }
        return null;
    }
}
