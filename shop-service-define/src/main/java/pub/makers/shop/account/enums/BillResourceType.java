package pub.makers.shop.account.enums;

/**
 * Created by dy on 2017/10/7.
 */
public enum BillResourceType {

    bill("结算"), deal("交易"), withdraw("提现"), refund("退款");

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    BillResourceType(String desc) {
        this.desc = desc;
    }
}
