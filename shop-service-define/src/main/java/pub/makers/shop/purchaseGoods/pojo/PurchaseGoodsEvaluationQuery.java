package pub.makers.shop.purchaseGoods.pojo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by kok on 2017/6/2.
 */
public class PurchaseGoodsEvaluationQuery implements Serializable {

    private String userId; // 用户id
    private String goodId; // 商品id
    private String orderId; //订单id
    private Integer type = 1; // 1：全部 2：晒图 3：好评 4：中评 5：差评
    private Integer pageNum = 1; // 分页页码
    private Integer pageSize = 10; // 分页大小


    public void bulidPurchaseGoodsQuery(HttpServletRequest request) {
        if (request.getParameter("pageNum") != null) {
            this.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
        } else {
            this.setPageNum(1);
        }
        if (request.getParameter("pageSize") != null) {
            this.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        } else {
            this.setPageSize(10);
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
