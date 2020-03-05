package pub.makers.shop.tradeOrder.enums;

/**
 * Created by dy on 2017/5/11.
 */
public enum IndentSource {

    subaccount("子账号", 2), weixin("微信", 1);

    private String name;
    private Integer dbData;

    IndentSource(String name, Integer dbData) {
        this.name = name;
        this.dbData = dbData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDbData() {
        return dbData;
    }

    public void setDbData(Integer dbData) {
        this.dbData = dbData;
    }

    @Override
    public String toString() {
        return this.dbData+"";
    }

    public static int getDbDataByName(String name) {
        for (IndentSource c : IndentSource.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

    public static String getTextByDbData(Integer status) {
        String result = "";
        if(status != null){
            for (IndentSource c : IndentSource.values()) {
                if (c.getDbData() == status) {
                    return c.name();
                }
            }
        }
        return result;
    }

    public static IndentSource getStatusByDbData(Integer status) {
        if(status != null){
            for (IndentSource c : IndentSource.values()) {
                if (c.getDbData() == status) {
                    return c;
                }
            }
        }
        return null;
    }
}


