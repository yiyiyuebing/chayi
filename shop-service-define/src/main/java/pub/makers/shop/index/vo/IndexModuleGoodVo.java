package pub.makers.shop.index.vo;

import pub.makers.shop.purchaseGoods.vo.PurchaseGoodsVo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kok on 2017/6/13.
 */
public class IndexModuleGoodVo implements Serializable {
    private String id;
    private String moduleId; // 模块ID
    private PurchaseGoodsVo good; //商品
    private Integer sort; // 排序
    private String isValid; // 是否有效
    private String delFlag; // 删除状态
    private Date dateCreated; // 创建时间
    private Date lastUpdated; // 更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public PurchaseGoodsVo getGood() {
        return good;
    }

    public void setGood(PurchaseGoodsVo good) {
        this.good = good;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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
