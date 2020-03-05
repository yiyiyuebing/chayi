package pub.makers.shop.promotion.vo;

import pub.makers.shop.promotion.enums.PromotionActivityType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kok on 2017/8/28.
 */
public class ManzengPromotionActivityVo extends PromotionActivityVo implements Serializable, Cloneable {
    private String id;
    /**
     * 大促标签
     */
    private String tag1;
    /**
     * 满减促销标签
     */
    private String tag2;
    /**
     * 赠品促销标签
     */
    private String tag3;
    /**
     * 大促标签启用
     */
    private String tag1Valid;
    /**
     * 促销标签启用
     */
    private String tag2Valid;
    /**
     * 满减促销说明
     */
    private String tag2SaleDesc;
    /**
     * 赠品促销说明
     */
    private String tag3SaleDesc;
    /**
     * 优惠方式(按金额/按件数)
     */
    private String discountType;
    /**
     * 优惠规则列表
     */
    private List<ManzengRuleVo> ruleList = new ArrayList<ManzengRuleVo>();

    public ManzengPromotionActivityVo() {
    }

    public ManzengPromotionActivityVo(ManzengActivityVo activityVo, PromotionActivityType type) {
        if (type != null) {
            this.setActivityType(type.name());
            this.setActivityName(type.getDisplayName());
        }
        this.setId(activityVo.getId());
        this.setActivityFullName(activityVo.getName());
        this.setActivityDesc(activityVo.getMemo());
        this.setTag1(activityVo.getTag1());
        this.setTag2(activityVo.getTag2());
        this.setTag3(activityVo.getTag3());
        this.setTag1Valid(activityVo.getTag1Valid());
        this.setTag2Valid(activityVo.getTag2Valid());
        this.setTag2SaleDesc(activityVo.getTag2SaleDesc());
        this.setTag3SaleDesc(activityVo.getTag3SaleDesc());
        this.setDiscountType(activityVo.getDiscountType());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
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

    public String getTag2SaleDesc() {
        return tag2SaleDesc;
    }

    public void setTag2SaleDesc(String tag2SaleDesc) {
        this.tag2SaleDesc = tag2SaleDesc;
    }

    public String getTag3SaleDesc() {
        return tag3SaleDesc;
    }

    public void setTag3SaleDesc(String tag3SaleDesc) {
        this.tag3SaleDesc = tag3SaleDesc;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public List<ManzengRuleVo> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<ManzengRuleVo> ruleList) {
        this.ruleList = ruleList;
    }

    public void addRule(ManzengRuleVo ruleVo) {
        this.ruleList.add(ruleVo);
    }

    @Override
    public ManzengPromotionActivityVo clone() throws CloneNotSupportedException {
        return (ManzengPromotionActivityVo) super.clone();
    }
}
