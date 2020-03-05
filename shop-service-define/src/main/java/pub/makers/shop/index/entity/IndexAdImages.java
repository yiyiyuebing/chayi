package pub.makers.shop.index.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/6/12.
 */
public class IndexAdImages implements Serializable {
    private String id;
    private String imgName;
    private String imageUrl;
    private String lnkUrl;
    private String postCode;
    private String classifyId;
    private Integer sort;
    private String isValid;
    private String delFlag ;
    private Date dateCreated ;
    private Date lastUpdated  ;
    private String linkDescribe;
    private String linkType;
    private String linkGoodId;
    private String noticeType;
    private String platform;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLnkUrl() {
        return lnkUrl;
    }

    public void setLnkUrl(String lnkUrl) {
        this.lnkUrl = lnkUrl;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
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

    public String getLinkDescribe() {
        return linkDescribe;
    }

    public void setLinkDescribe(String linkDescribe) {
        this.linkDescribe = linkDescribe;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkGoodId() {
        return linkGoodId;
    }

    public void setLinkGoodId(String linkGoodId) {
        this.linkGoodId = linkGoodId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
