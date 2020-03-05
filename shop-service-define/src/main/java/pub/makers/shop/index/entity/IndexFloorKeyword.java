package pub.makers.shop.index.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/6/12.
 */
public class IndexFloorKeyword implements Serializable {
    private String id;
    private String floorId; // 楼层ID
    private String keyword; // 关键字内容
    private String keywordUrl; // 关键字链接
    private String postCode; // 位置代码(左侧/右侧)
    private String iconUrl; // 图标地址
    private Integer sort; // 排序
    private String isValid; // 是否有效
    private String delFlag; // 删除状态
    private Date dateCreated; // 创建时间
    private Date lastUpdated; // 更新时间
    private String keywordUrlDescribe;// 关键字链接描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordUrl() {
        return keywordUrl;
    }

    public void setKeywordUrl(String keywordUrl) {
        this.keywordUrl = keywordUrl;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public String getKeywordUrlDescribe() {
        return keywordUrlDescribe;
    }

    public void setKeywordUrlDescribe(String keywordUrlDescribe) {
        this.keywordUrlDescribe = keywordUrlDescribe;
    }
}
