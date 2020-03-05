package pub.makers.shop.bill.enums;

/**
 * Created by dy on 2017/9/8.
 */
public enum MqTaskType {

    u8("u8"), bill("账单");

    private String displayName;

    MqTaskType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
