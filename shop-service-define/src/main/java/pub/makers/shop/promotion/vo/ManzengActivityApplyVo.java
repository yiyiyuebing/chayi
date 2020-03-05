package pub.makers.shop.promotion.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/8/24.
 */
public class ManzengActivityApplyVo implements Serializable {

    private Long id;

    private String activityId;

    private String applyScope;

    private String goodIds;

    private Integer sort;

    private String isValid;

    private String delFlag;

    private Date dateCreated;

    private Date lastUpdated;

    private String classifyIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getApplyScope() {
        return applyScope;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }

    public String getGoodIds() {
        return goodIds;
    }

    public void setGoodIds(String goodIds) {
        this.goodIds = goodIds;
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

    public String getClassifyIds() {
        return classifyIds;
    }

    public void setClassifyIds(String classifyIds) {
        this.classifyIds = classifyIds;
    }
}
