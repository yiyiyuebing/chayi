package pub.makers.shop.tradeGoods.vo;


import pub.makers.shop.tradeGoods.entity.TradeGiftRule;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/5/25.
 */
public class TradeGiftRuleVo implements Serializable {

    /**  */
    private String ruleId;

    /** 商品ID */
    private String goodId;

    /** 商品库存ID */
    private String goodSkuId;

    /** 赠品ID */
    private String giftId;

    /** 赠品库存ID */
    private String giftSkuId;

    /** 赠品名称 */
    private String giftName;

    /**  */
    private String intro;

    /**  */
    private Integer num;

    private Integer condNum;
    /*  赠品规则（mulriple倍数送 range区间送overlay叠加送one限送1） */
    private String condType;

    /** 是否有效 */
    private String isValid;

    /** 删除状态 */
    private String delFlag;

    /** 创建时间 */
    private Date dateCreated;

    /** 更新时间 */
    private Date lastUpdated;

    private String goodType;


    public TradeGiftRuleVo(TradeGiftRule tradeGiftRule) {
        this.ruleId = tradeGiftRule.getRuleId() + "";
        this.goodId = tradeGiftRule.getGoodId() + "";
        this.goodSkuId = tradeGiftRule.getGoodSkuId() + "";
        this.giftId = tradeGiftRule.getGiftId() + "";
        this.giftSkuId = tradeGiftRule.getGiftSkuId() + "";
        this.giftName = tradeGiftRule.getGiftName();
        this.intro = tradeGiftRule.getIntro();
        this.num = tradeGiftRule.getNum();
        this.isValid = tradeGiftRule.getIsValid();
        this.delFlag = tradeGiftRule.getDelFlag();
        this.dateCreated = tradeGiftRule.getDateCreated();
        this.lastUpdated = tradeGiftRule.getLastUpdated();
        this.goodType = tradeGiftRule.getGoodType();

    }


    public TradeGiftRuleVo() {
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(String goodSkuId) {
        this.goodSkuId = goodSkuId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftSkuId() {
        return giftSkuId;
    }

    public void setGiftSkuId(String giftSkuId) {
        this.giftSkuId = giftSkuId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public Integer getCondNum() {
        return condNum;
    }

    public void setCondNum(Integer condNum) {
        this.condNum = condNum;
    }

    public String getCondType() {
        return condType;
    }

    public void setCondType(String condType) {
        this.condType = condType;
    }
}
