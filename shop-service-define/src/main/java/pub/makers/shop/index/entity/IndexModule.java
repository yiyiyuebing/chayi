package pub.makers.shop.index.entity;

import pub.makers.shop.index.vo.IndexModuleGoodVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/6/12.
 */
public class IndexModule implements Serializable {
    private String id;
    private String classifyId; // 楼层商品分类
    private String moduleName; // 模块名称
    private String leftAd; // 左侧广告图
    private String leftAdUrl; // 左侧广告图链接
    private Integer sort; // 排序
    private String isValid; // 是否有效
    private String delFlag; // 删除状态
    private Date dateCreated; // 创建时间
    private Date lastUpdated; // 更新时间
    private String moduleType; // 模块类别(热卖专区/首页楼层)
    private String leftAdUrlDescribe; // 左侧广告图链接描述

    private List<IndexModuleGoodVo> indexModuleGoodVoList;

    private List<IndexModuleGood> indexModuleGoodList;

    private List<IndexFloorKeyword> floorKeywords;

    private List<IndexFloorKeyword> floorTopKeywords;

    private IndexAdImages indexAdImages;

    public List<IndexFloorKeyword> getFloorTopKeywords() {
        return floorTopKeywords;
    }

    public void setFloorTopKeywords(List<IndexFloorKeyword> floorTopKeywords) {
        this.floorTopKeywords = floorTopKeywords;
    }

    public IndexAdImages getIndexAdImages() {
        return indexAdImages;
    }

    public void setIndexAdImages(IndexAdImages indexAdImages) {
        this.indexAdImages = indexAdImages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getLeftAd() {
        return leftAd;
    }

    public void setLeftAd(String leftAd) {
        this.leftAd = leftAd;
    }

    public String getLeftAdUrl() {
        return leftAdUrl;
    }

    public void setLeftAdUrl(String leftAdUrl) {
        this.leftAdUrl = leftAdUrl;
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

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public List<IndexModuleGoodVo> getIndexModuleGoodVoList() {
        return indexModuleGoodVoList;
    }

    public void setIndexModuleGoodVoList(List<IndexModuleGoodVo> indexModuleGoodVoList) {
        this.indexModuleGoodVoList = indexModuleGoodVoList;
    }

    public List<IndexFloorKeyword> getFloorKeywords() {
        return floorKeywords;
    }

    public void setFloorKeywords(List<IndexFloorKeyword> floorKeywords) {
        this.floorKeywords = floorKeywords;
    }

    public List<IndexModuleGood> getIndexModuleGoodList() {
        return indexModuleGoodList;
    }

    public void setIndexModuleGoodList(List<IndexModuleGood> indexModuleGoodList) {
        this.indexModuleGoodList = indexModuleGoodList;
    }

    public String getLeftAdUrlDescribe() {
        return leftAdUrlDescribe;
    }

    public void setLeftAdUrlDescribe(String leftAdUrlDescribe) {
        this.leftAdUrlDescribe = leftAdUrlDescribe;
    }
}
