package pub.makers.shop.purchaseGoods.vo;

import java.io.Serializable;

/**
 * Created by dy on 2017/5/27.
 */
public class ClassifyParams implements Serializable {
    private String id;
    private String status;
    private String parentId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}