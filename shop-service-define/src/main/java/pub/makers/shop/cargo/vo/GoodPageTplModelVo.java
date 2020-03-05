package pub.makers.shop.cargo.vo;

import pub.makers.shop.baseGood.vo.BaseGoodVo;
import pub.makers.shop.cargo.entity.GoodPageTplModel;
import pub.makers.shop.store.vo.ImageVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by kok on 2017/6/8.
 */
public class GoodPageTplModelVo implements Serializable {
    private String id;
    private String tplId;
    private String tplClassCode;
    private String postCode;
    private String modelName;
    private String modelType;
    private String goodIds;
    private String adUrl;
    private String adImgId;
    private List<ImageVo> adImgList;
    private String sort;
    private String createBy;
    private String isValid;
    private String delFlag;
    private Date dateCreated;
    private Date lastUpdated;
    private String detail;
    private Integer num;
    private String linkDescribe;
    private List<BaseGoodVo> goodList;

    public String getLinkDescribe() {
        return linkDescribe;
    }

    public void setLinkDescribe(String linkDescribe) {
        this.linkDescribe = linkDescribe;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getGoodIds() {
        return goodIds;
    }

    public void setGoodIds(String goodIds) {
        this.goodIds = goodIds;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdImgId() {
        return adImgId;
    }

    public void setAdImgId(String adImgId) {
        this.adImgId = adImgId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public List<BaseGoodVo> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<BaseGoodVo> goodList) {
        this.goodList = goodList;
    }

    public List<ImageVo> getAdImgList() {
        return adImgList;
    }

    public void setAdImgList(List<ImageVo> adImgList) {
        this.adImgList = adImgList;
    }

    public static GoodPageTplModelVo fromGoodPageTplModel(GoodPageTplModel model) {
        GoodPageTplModelVo vo = new GoodPageTplModelVo();
        vo.setId(model.getId() == null ? null : model.getId().toString());
        vo.setTplId(model.getTplId() == null ? null : model.getTplId().toString());
        vo.setTplClassCode(model.getTplClassCode());
        vo.setPostCode(model.getPostCode());
        vo.setModelName(model.getModelName());
        vo.setModelType(model.getModelType());
        vo.setGoodIds(model.getGoodIds());
        vo.setAdUrl(model.getAdUrl());
        vo.setAdImgId(model.getAdImgId());
        vo.setSort(model.getSort() == null ? null : model.getSort().toString());
        vo.setCreateBy(model.getCreateBy() == null ? null : model.getCreateBy().toString());
        vo.setIsValid(model.getIsValid());
        vo.setDelFlag(model.getDelFlag());
        vo.setDetail(model.getDetail());
        vo.setDateCreated(model.getDateCreated());
        vo.setLastUpdated(model.getLastUpdated());
        vo.setLinkDescribe(model.getLinkDescribe());
        return vo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
