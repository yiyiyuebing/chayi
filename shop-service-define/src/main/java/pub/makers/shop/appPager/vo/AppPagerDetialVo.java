package pub.makers.shop.appPager.vo;

import pub.makers.shop.appPager.entity.AppModuleExtra;
import pub.makers.shop.appPager.entity.AppPager;
import pub.makers.shop.appPager.entity.AppPagerModule;
import pub.makers.shop.appPager.entity.AppPagerModuleGood;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by devpc on 2017/9/1.
 */
public class AppPagerDetialVo implements Serializable {

    private String id;

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

    private List<AppPagerModule> appPagerModuleList;//详细

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public List<AppPagerModule> getAppPagerModuleList() {
        return appPagerModuleList;
    }

    public void setAppPagerModuleList(List<AppPagerModule> appPagerModuleList) {
        this.appPagerModuleList = appPagerModuleList;
    }

    public String getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(String tagGroup) {
        this.tagGroup = tagGroup;
    }

    public AppPagerDetialVo() {
        super();
    }

    public AppPagerDetialVo(AppPager appPager) {
        this.id = String.valueOf(appPager.getId());
        this.title = appPager.getTitle();
        this.background = appPager.getBackground();
        this.color = appPager.getColor();
        this.sort = appPager.getSort();
        this.orderBizType = appPager.getOrderBizType();
        this.description = appPager.getDescription();
        this.tagGroup = appPager.getTagGroup();
    }
}
