package pub.makers.shop.base.enums;

/**
 * Created by Think on 2017/8/25.
 */
public enum  OpinionType {

    bug("bug",1),advice("advice",2);

    private String name;
    private int dbData;


    private OpinionType(String name, int dbData) {
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
        return this.dbData + "";
    }

    public static int getDbDataByName(String name) {
        for (OpinionType c : OpinionType.values()) {
            if (c.getName().equals(name)) {
                return c.dbData;
            }
        }
        return 0;
    }

    public static String getTextByDbData(Integer status) {
        String result = "";
        if (status != null) {
            for (OpinionType c : OpinionType.values()) {
                if (c.getDbData() == status) {
                    return c.name();
                }
            }
        }
        return result;
    }

    public static OpinionType getStatusByDbData(Integer status) {
        if (status != null) {
            for (OpinionType c : OpinionType.values()) {
                if (c.getDbData() == status) {
                    return c;
                }
            }
        }
        return null;
    }
}
