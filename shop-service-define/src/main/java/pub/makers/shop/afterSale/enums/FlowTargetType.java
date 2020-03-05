package pub.makers.shop.afterSale.enums;

/**
 * Created by kok on 2017/7/7.
 */
public enum FlowTargetType {
    order("订单售后"),list("订单商品售后");

    private String displayName;

    FlowTargetType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
