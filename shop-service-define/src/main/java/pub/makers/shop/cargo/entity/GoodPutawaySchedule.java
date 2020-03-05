package pub.makers.shop.cargo.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by daiwenfa on 2017/6/1.
 */
public class GoodPutawaySchedule implements Serializable {
    private Long id;
    private Long goodId;//商品id
    private String goodType;//商品类别
    private Date shelfTime;//定时上架时间
    private Integer num;//定时上架数量
    private String operResult;//定时上架结果
    private Long operMan;//定时上级人
    private Long skuId;//skuId
    private String isValid;//是否定时上级
    private String delFlag;//是否删除
    private Date dateCreate;//创建时间
    private Date lastUpdated;//更改时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOperResult() {
        return operResult;
    }

    public void setOperResult(String operResult) {
        this.operResult = operResult;
    }

    public Long getOperMan() {
        return operMan;
    }

    public void setOperMan(Long operMan) {
        this.operMan = operMan;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
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

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getShelfTime() {
        return shelfTime;
    }

    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }
}
