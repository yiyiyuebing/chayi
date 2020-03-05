package pub.makers.shop.cargo.entity.vo;

import pub.makers.shop.store.vo.ImageVo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/5/24.
 */
public class CargoBrandVo implements Serializable {

    private String id;

    private String logo;

    private String pcLogo;

    private String name;

    private String url;

    private String supplierName;

    private Integer brandRecommendation;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private Integer sort;

    private Integer orderBy;//排序

    private ImageVo imageVo;

    private String delFlag;

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public ImageVo getImageVo() {
        return imageVo;
    }

    public void setImageVo(ImageVo imageVo) {
        this.imageVo = imageVo;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public String getPcLogo() {
        return pcLogo;
    }

    public void setPcLogo(String pcLogo) {
        this.pcLogo = pcLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getBrandRecommendation() {
        return brandRecommendation;
    }

    public void setBrandRecommendation(Integer brandRecommendation) {
        this.brandRecommendation = brandRecommendation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
}
