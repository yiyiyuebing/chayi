package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public class GoodPageTplPost implements Serializable {
    private Long id;
    private String tplClassCode;
    private String postCode;
    private String postName;
    private String ctlType;
    private Long sort;
    private String multi;
    private String isValid;
    private String delFlag;
    private Date dateCreated;
    private Date lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTplClassCode() {
        return tplClassCode;
    }

    public void setTplClassCode(String tplClassCode) {
        this.tplClassCode = tplClassCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getCtlType() {
        return ctlType;
    }

    public void setCtlType(String ctlType) {
        this.ctlType = ctlType;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getMulti() {
        return multi;
    }

    public void setMulti(String multi) {
        this.multi = multi;
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
