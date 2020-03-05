package pub.makers.shop.afterSale.enums;

/**
 * Created by dy on 2017/5/15.
 */
public enum OrderFlowStatus {

    please_refund("请退款", OrderAsType.refund, 1), refund("同意退款", OrderAsType.refund, 2), success_refund("退款成功", OrderAsType.refund, 5), cancel_refund("已撤销退款", OrderAsType.refund, 6), refuse_refund("已拒绝退款", OrderAsType.refund, 7),
    please_exchange("请换货", OrderAsType.exchange, 1), exchange("同意换货", OrderAsType.exchange, 2), exc_shipping("买家发货", OrderAsType.exchange, 3), exc_confirm_receive("确认到货", OrderAsType.exchange, 4), success_exchange("换货成功", OrderAsType.exchange, 5), cancel_exchange("已撤销换货", OrderAsType.exchange, 6), refuse_exchange("已拒绝换货", OrderAsType.exchange, 7),
    please_return("请退货", OrderAsType.refund_return, 1), return_refund("同意退货", OrderAsType.refund_return, 2), ret_shipping("买家发货", OrderAsType.refund_return, 3), ret_confirm_receive("确认到货", OrderAsType.refund_return, 4), success_return("退货退款成功", OrderAsType.refund_return, 5), cancel_return("已撤销退货", OrderAsType.refund_return, 6), refuse_return("已拒绝退货退款", OrderAsType.refund_return, 7);

    private String displayName;

    private OrderAsType asType;

    private Integer queryNum;

    public String getDisplayName() {
        return displayName;
    }

    public OrderAsType getAsType() {
        return asType;
    }

    public Integer getQueryNum() {
        return queryNum;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    OrderFlowStatus(String displayName, OrderAsType asType, Integer queryNum) {
        this.displayName = displayName;
        this.asType = asType;
        this.queryNum = queryNum;
    }

    public static OrderFlowStatus getStatus(OrderAsType asType, Integer queryNum) {
        for (OrderFlowStatus flowStatus : OrderFlowStatus.values()) {
            if (flowStatus.getAsType().equals(asType) && flowStatus.getQueryNum().equals(queryNum)) {
                return flowStatus;
            }
        }
        return null;
    }
}
