package pub.makers.shop.bill.param;

import java.io.Serializable;

/**
 * Created by dy on 2017/9/8.
 */
public class OrderBillParam implements Serializable {

    private String orderId;
    private Long taskId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
