package pub.makers.shop.promotion.vo;

import java.io.Serializable;

/**
 * Created by kok on 2017/8/21.
 */
public class SalePromotionActivityVo extends PromotionActivityVo implements Serializable {
    private String id;

    /** 打折方式(打折/优惠)*/
    private String saleType;

    /** 是否限购 */
    private String limitFlg;

    /** 限购数量 */
    private Integer limitNum;

    /** 限购数量单位 */
    private String limitUnit;

    /** 虚拟抢定人数 */
    private Integer vmNum;

    /** 抢定人数 */
    private Integer buyNum;

    /** 可售卖数量 */
    private Integer maxNum;

    /** 大促标签 */
    private String tag1;

    /** 促销标签 */
    private String tag2;

    /** 大促标签启用 */
    private String tag1Valid;

    /** 促销标签启用 */
    private String tag2Valid;

    /** 活动备注 */
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getLimitFlg() {
        return limitFlg;
    }

    public void setLimitFlg(String limitFlg) {
        this.limitFlg = limitFlg;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getLimitUnit() {
        return limitUnit;
    }

    public void setLimitUnit(String limitUnit) {
        this.limitUnit = limitUnit;
    }

    public Integer getVmNum() {
        return vmNum;
    }

    public void setVmNum(Integer vmNum) {
        this.vmNum = vmNum;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag1Valid() {
        return tag1Valid;
    }

    public void setTag1Valid(String tag1Valid) {
        this.tag1Valid = tag1Valid;
    }

    public String getTag2Valid() {
        return tag2Valid;
    }

    public void setTag2Valid(String tag2Valid) {
        this.tag2Valid = tag2Valid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
