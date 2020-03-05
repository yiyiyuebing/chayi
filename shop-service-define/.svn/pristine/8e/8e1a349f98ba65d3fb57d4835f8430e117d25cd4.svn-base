package pub.makers.shop.cargo.vo;

import pub.makers.shop.cargo.entity.GoodPageTpl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by kok on 2017/6/8.
 */
public class GoodPageTplVo implements Serializable {
    private String id;
    private String tplClassCode;
    private String tplName;
    private String intro;
    private String orderBizType;
    private String createBy;
    private String isValid;
    private String delFlag;
    private Date dateCreated;
    private Date lastUpdated;
    private List<GoodPageTplModelVo> modelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTplClassCode() {
        return tplClassCode;
    }

    public void setTplClassCode(String tplClassCode) {
        this.tplClassCode = tplClassCode;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType;
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

    public List<GoodPageTplModelVo> getModelList() {
        return modelList;
    }

    public void setModelList(List<GoodPageTplModelVo> modelList) {
        this.modelList = modelList;
    }

    public static GoodPageTplVo fromGoodPageTpl(GoodPageTpl tpl) {
        GoodPageTplVo vo = new GoodPageTplVo();
        vo.setId(tpl.getId() == null ? null : tpl.getId().toString());
        vo.setTplClassCode(tpl.getTplClassCode());
        vo.setTplName(tpl.getTplName());
        vo.setIntro(tpl.getIntro());
        vo.setOrderBizType(tpl.getOrderBizType());
        vo.setCreateBy(tpl.getCreateBy());
        vo.setIsValid(tpl.getIsValid());
        vo.setDelFlag(tpl.getDelFlag());
        vo.setDateCreated(tpl.getDateCreated());
        vo.setLastUpdated(tpl.getLastUpdated());
        return vo;
    }
}
