package pub.makers.shop.message.enums;

/**
 * Created by dy on 2017/4/17.
 */
public enum MessageType {
    notice("通知",0),news("消息",1),review("审核",2),error("异常单", 10);

    private String name;
    private int dbData;


    private MessageType(String name,int dbData) {
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
        for (MessageType c : MessageType.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

    public static String getTextByDbData(Integer status) {
        String result = "";
        if(status != null){
            for (MessageType c : MessageType.values()) {
                if (c.getDbData() == status) {
                    return c.name();
                }
            }
        }
        return result;
    }

    public static MessageType getStatusByDbData(Integer status) {
        if(status != null){
            for (MessageType c : MessageType.values()) {
                if (c.getDbData() == status) {
                    return c;
                }
            }
        }
        return null;
    }
}