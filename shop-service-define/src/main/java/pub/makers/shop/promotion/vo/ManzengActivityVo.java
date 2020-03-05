package pub.makers.shop.promotion.vo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.promotion.entity.ManzengActivityApply;
import pub.makers.shop.promotion.enums.SaleActivityScope;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/8/19.
 */
public class ManzengActivityVo implements Serializable {

    private String id;

    private String orderBizType;

    private String name;

    private String tag1;

    private String tag2;

    private String tag3;

    private String tag1Valid;

    private String tag2Valid;

    private String saleDesc;

    private Date startTime;

    private Date endTime;

    private String memo;

    private String discountType;

    private String createBy;

    private String limitFlag;

    private Integer limitNum;

    private String limitUnit;

    private String isValid;

    private String delFlag;

    private Date dateCreated;

    private Date lastUpdated;

    private String tag2SaleDesc;

    private String tag3SaleDesc;

    private String applyScope;

    private Integer goodNum;

    private String imageUrl;

    private ImageGroupVo imageGroup;

    private List<ManzengRuleVo> manzengRuleVoList;

    private ManzengActivityApply apply;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public String getApplyScope() {
        if (StringUtils.isNotBlank(applyScope)) {
            return SaleActivityScope.valueOf(applyScope).getDisplayName();
        }
        return null;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }

    public String getTag2SaleDesc() {
        return tag2SaleDesc;
    }

    public void setTag2SaleDesc(String tag2SaleDesc) {
        this.tag2SaleDesc = tag2SaleDesc;
    }

    public ImageGroupVo getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(ImageGroupVo imageGroup) {
        this.imageGroup = imageGroup;
    }

    public List<ManzengRuleVo> getManzengRuleVoList() {
        return manzengRuleVoList;
    }

    public void setManzengRuleVoList(List<ManzengRuleVo> manzengRuleVoList) {
        this.manzengRuleVoList = manzengRuleVoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(String orderBizType) {
        this.orderBizType = orderBizType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSaleDesc() {
        return saleDesc;
    }

    public void setSaleDesc(String saleDesc) {
        this.saleDesc = saleDesc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLimitFlag() {
        return limitFlag;
    }

    public void setLimitFlag(String limitFlag) {
        this.limitFlag = limitFlag;
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

    public ManzengActivityApply getApply() {
        return apply;
    }

    public void setApply(ManzengActivityApply apply) {
        this.apply = apply;
    }

    public String getTag3SaleDesc() {
        return tag3SaleDesc;
    }

    public void setTag3SaleDesc(String tag3SaleDesc) {
        this.tag3SaleDesc = tag3SaleDesc;
    }
}
