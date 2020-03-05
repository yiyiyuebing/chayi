package pub.makers.shop.afterSale.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kok on 2017/6/17.
 */
public class OrderItemReplyImgVo implements Serializable {
    private String id;
    private String orderItemReplyId;
    private String imgUrl;
    private String imgDesc;
    /**
     * 创建时间
     */
    private Date dateCreated;
    /**
     * 更新时间
     */
    private Date lastUpdated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderItemReplyId() {
        return orderItemReplyId;
    }

    public void setOrderItemReplyId(String orderItemReplyId) {
        this.orderItemReplyId = orderItemReplyId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
