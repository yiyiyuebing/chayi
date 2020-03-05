package pub.makers.shop.tongji.vo;

import java.io.Serializable;

/**
 * Created by devpc on 2017/8/16.
 */
public class TopTenShopTotalKpi implements Serializable{

    /**店铺**/
    private String shopId;
    private String deptCode;
    private String headImgUrl;
    private String custumName;


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getCustumName() {
        return custumName;
    }

    public void setCustumName(String custumName) {
        this.custumName = custumName;
    }
}
