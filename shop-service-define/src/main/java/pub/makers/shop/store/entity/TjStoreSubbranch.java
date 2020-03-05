package pub.makers.shop.store.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by daiwenfa on 2017/7/25.
 */
public class TjStoreSubbranch implements Serializable {
    private Long id;
    private Long storeId;//店铺id
    private String name;//店铺名称
    private String headImgUrl;//头像图片
    private String concatName;//客户名称
    private String departmentNum;//部门编码
    private String number;//店铺编码
    private String label;//标签
    private Long levelId;//店铺等级Id
    private Integer sonNum;//子账号数量
    private String receivingArea;//收货地址
    private BigDecimal totalTranAmount;//实际交易总金额
    private Integer totalTranNum;//交易笔数
    private Integer totalBuyNum;//购买件数
    private BigDecimal avgTranAmount;//平均交易金额
    private Date lastTranTime;//上次交易时间
    private Date secondLastTranTime;//倒数第二次购买时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getConcatName() {
        return concatName;
    }

    public void setConcatName(String concatName) {
        this.concatName = concatName;
    }

    public String getDepartmentNum() {
        return departmentNum;
    }

    public void setDepartmentNum(String departmentNum) {
        this.departmentNum = departmentNum;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Integer getSonNum() {
        return sonNum;
    }

    public void setSonNum(Integer sonNum) {
        this.sonNum = sonNum;
    }

    public String getReceivingArea() {
        return receivingArea;
    }

    public void setReceivingArea(String receivingArea) {
        this.receivingArea = receivingArea;
    }

    public BigDecimal getTotalTranAmount() {
        return totalTranAmount;
    }

    public void setTotalTranAmount(BigDecimal totalTranAmount) {
        this.totalTranAmount = totalTranAmount;
    }

    public Integer getTotalTranNum() {
        return totalTranNum;
    }

    public void setTotalTranNum(Integer totalTranNum) {
        this.totalTranNum = totalTranNum;
    }

    public Integer getTotalBuyNum() {
        return totalBuyNum;
    }

    public void setTotalBuyNum(Integer totalBuyNum) {
        this.totalBuyNum = totalBuyNum;
    }

    public BigDecimal getAvgTranAmount() {
        return avgTranAmount;
    }

    public void setAvgTranAmount(BigDecimal avgTranAmount) {
        this.avgTranAmount = avgTranAmount;
    }

    public Date getLastTranTime() {
        return lastTranTime;
    }

    public void setLastTranTime(Date lastTranTime) {
        this.lastTranTime = lastTranTime;
    }

    public Date getSecondLastTranTime() {
        return secondLastTranTime;
    }

    public void setSecondLastTranTime(Date secondLastTranTime) {
        this.secondLastTranTime = secondLastTranTime;
    }
}
