package pub.makers.shop.store.vo;

import pub.makers.shop.base.entity.SysDict;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/7/27.
 */
public class StoreSubbranceExtVo  implements Serializable {
    private String id;
    private String storeId;
    private String remark;
    private String label;
    private String delFlag;
    private String isValid;
    private Date dateCreated;
    private Date lastUpdated;
    List<SysDict> sysDictsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<SysDict> getSysDictsList() {
        return sysDictsList;
    }

    public void setSysDictsList(List<SysDict> sysDictsList) {
        this.sysDictsList = sysDictsList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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
