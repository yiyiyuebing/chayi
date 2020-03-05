package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public class GoodPageTplApply implements Serializable {
    private Long id;
    private Long tplId;
    private String applyScope;
    private String goodIds;
    private String ClassifyIds;
    private Long sort;
    private String isValid;
    private String delFlag;


    public String getGoodIds() {
        return goodIds;
    }

    public void setGoodIds(String goodIds) {
        this.goodIds = goodIds;
    }

    public String getClassifyIds() {
        return ClassifyIds;
    }

    public void setClassifyIds(String classifyIds) {
        ClassifyIds = classifyIds;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
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

    private Date dateCreated;
    private Date lastUpdated;

    public Long getTplId() {
        return tplId;
    }

    public void setTplId(Long tplId) {
        this.tplId = tplId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyScope() {
        return applyScope;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }
}
