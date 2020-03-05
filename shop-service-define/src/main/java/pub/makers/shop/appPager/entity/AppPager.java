package pub.makers.shop.appPager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppPager implements Serializable {
    private Long id;

    private String title;//标题

    private String background;//背景颜色

    private String color;//字体颜色

    private Integer sort;//排序

    private String isValid;//是否有效

    private String delFlag;//删除标识

    private Date dateCreated;//创建时间

    private Date lastUpdated;//修改时间

    private String orderBizType;

    private String description;

    private String tagGroup;

    private List<AppPagerModule> appPagerModuleList;

    public List<AppPagerModule> getAppPagerModuleList() {
        return appPagerModuleList;
    }

    public void setAppPagerModuleList(List<AppPagerModule> appPagerModuleList) {
        this.appPagerModuleList = appPagerModuleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background == null ? null : background.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
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
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
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

    public String getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType == null ? null : orderBizType.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(String tagGroup) {
        this.tagGroup = tagGroup == null ? null : tagGroup.trim();
    }
}