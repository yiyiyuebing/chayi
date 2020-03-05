package pub.makers.shop.user.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dy on 2017/10/8.
 */
public class WeixinFansParam implements Serializable {

    private String shopId; //店铺ID
    private String orderBy; //排序字段 （关注时间，订单数量，订单金额）
    private String orderType = "desc";   //排序方式（升序：asc，倒序：desc）

    private String fansId; //粉丝ID

    private String label;

    private String shopIds;

    private List<String> belongTos;

    private String queryType;

    private String searchKeyword;

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public List<String> getBelongTos() {
        return belongTos;
    }

    public void setBelongTos(List<String> belongTos) {
        this.belongTos = belongTos;
    }

    public String getShopIds() {
        return shopIds;
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFansId() {
        return fansId;
    }

    public void setFansId(String fansId) {
        this.fansId = fansId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
