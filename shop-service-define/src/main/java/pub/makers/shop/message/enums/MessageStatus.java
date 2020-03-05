package pub.makers.shop.message.enums;

/**
 * 消息状态枚举
 * Created by dy on 2017/4/14.
 */
public enum MessageStatus {

    notice("消息回复",0),noticeShip1("提醒发货",1),ship("待发货",2),refund1("申请退款",3),review("审核通知",4),refund2("申请退货",5),error("异常单",20)
    ,noticeShip2("提醒进货发货",11),noticeShip("待进货发货",12),refund3("申请进货退款",13),refund4("申请进货退货",15);//申请进货退货 没有做

    private String name;
    private int dbData;


    private MessageStatus(String name,int dbData) {
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
        for (MessageStatus c : MessageStatus.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

    public static String getTextByDbData(Integer status) {
        String result = "";
        if(status != null){
            for (MessageStatus c : MessageStatus.values()) {
                if (c.getDbData() == status) {
                    return c.name();
                }
            }
        }
        return result;
    }

    public static MessageStatus getStatusByDbData(Integer status) {
        if(status != null){
            for (MessageStatus c : MessageStatus.values()) {
                if (c.getDbData() == status) {
                    return c;
                }
            }
        }
        return null;
    }
}
