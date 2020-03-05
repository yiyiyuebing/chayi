package pub.makers.shop.purchaseGoods.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
public class PurchaseClassify implements Serializable {

    private String id;   //主键id
    private String parentId;      //父级id
    private Integer status;     //状态，0禁用，1启用
    private Integer orderIndex; //排序
    private String name;        //名称
    private Date createTime;    //创建时间
    private Long createBy;      //创建者id
    private Date updateTime;    //修改时间
    private Long updateBy;      //修改时间
    private String imgUrl;      //图片地址
    private String storeLevel;  //存储等级
    private PurchaseClassify parent;  //父级对象
    private List<PurchaseClassify> children;  //子集节点
    private List<PurchaseClassifyAttr> attrs; //属性集合
    public  String purType;//进货类型

    private String type;    //类型， 1.分类，2.属性，3.品牌
    private String priceRange;
    private String storeLevelName;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStoreLevel() {
        return storeLevel;
    }

    public void setStoreLevel(String storeLevel) {
        this.storeLevel = storeLevel;
    }

    public PurchaseClassify getParent() {
        return parent;
    }

    public void setParent(PurchaseClassify parent) {
        this.parent = parent;
    }

    public List<PurchaseClassify> getChildren() {
        return children;
    }

    public void setChildren(List<PurchaseClassify> children) {
        this.children = children;
    }

    public List<PurchaseClassifyAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<PurchaseClassifyAttr> attrs) {
        this.attrs = attrs;
    }

    public String getPurType() {
        return purType;
    }

    public void setPurType(String purType) {
        this.purType = purType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getStoreLevelName() {
        return storeLevelName;
    }

    public void setStoreLevelName(String storeLevelName) {
        this.storeLevelName = storeLevelName;
    }
}
