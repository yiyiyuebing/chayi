package pub.makers.shop.purchaseOrder.vo;

import java.io.Serializable;

/**
 * Created by kok on 2017/6/17.
 */
public class PurchaseOrderCountVo implements Serializable {
    private Long allCount; // 所有订单数
    private Long payCount; // 待支付订单数
    private Long shipCount; // 代发货订单数
    private Long receiveCount; // 待收获订单数
    private Long evaluateCount; // 待评价订单数
    private Long doneCount; // 已完成订单数

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getPayCount() {
        return payCount;
    }

    public void setPayCount(Long payCount) {
        this.payCount = payCount;
    }

    public Long getShipCount() {
        return shipCount;
    }

    public void setShipCount(Long shipCount) {
        this.shipCount = shipCount;
    }

    public Long getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(Long receiveCount) {
        this.receiveCount = receiveCount;
    }

    public Long getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Long evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public Long getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(Long doneCount) {
        this.doneCount = doneCount;
    }
}
