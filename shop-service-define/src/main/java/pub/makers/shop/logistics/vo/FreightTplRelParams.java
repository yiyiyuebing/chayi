package pub.makers.shop.logistics.vo;

import java.io.Serializable;

/**
 * Created by dy on 2017/4/20.
 */
public class FreightTplRelParams implements Serializable {

    private String tplName;
    private String relType;
    private String orderType;
    private String relIds;
    private String sort;
    private String isValid;

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getRelIds() {
        return relIds;
    }

    public void setRelIds(String relIds) {
        this.relIds = relIds;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
