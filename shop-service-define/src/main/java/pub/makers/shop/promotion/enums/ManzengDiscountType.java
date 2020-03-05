package pub.makers.shop.promotion.enums;

/**
 * Created by dy on 2017/8/19.
 */
public enum ManzengDiscountType {

    money("金额"), piece("件数");

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private ManzengDiscountType(String displayName){
        this.displayName = displayName;
    }

}
