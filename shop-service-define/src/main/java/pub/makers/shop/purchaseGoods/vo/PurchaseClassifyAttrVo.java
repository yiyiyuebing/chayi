package pub.makers.shop.purchaseGoods.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/26.
 */
public class PurchaseClassifyAttrVo implements Serializable {
    private String id;   //主键id
    private String parentId;      //父级id
    private Integer status;     //状态，0禁用，1启用
    private Integer orderIndex; //排序
    private String name;        //名称
    private Date createTime;    //创建时间
    private Long createBy;      //创建者id
    private Date updateTime;    //修改时间
    private Long updateBy;      //修改时间
    private String purClassifyId;      //分类id
    private List<PurchaseClassifyAttrVo> children;  //子集属性
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPurClassifyId() {
        return purClassifyId;
    }

    public void setPurClassifyId(String purClassifyId) {
        this.purClassifyId = purClassifyId;
    }

    public List<PurchaseClassifyAttrVo> getChildren() {
        return children;
    }

    public void setChildren(List<PurchaseClassifyAttrVo> children) {
        this.children = children;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
