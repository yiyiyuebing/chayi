package pub.makers.shop.appPager.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppPagerModule implements Serializable {
    private Long id;

    private Long pageId;//微页面ID

    private String title;//标题

    private String subTitle;//副标题

    private String type;//组件类型

    private String fullScreen;//是否全屏

    private Integer goodsNumber;//商品数量

    private String showList;//显示类型/列表类型

    private String showListType;//显示类型样式'

    private String buyBtn;//显示购买按钮

    private String buyBtnType;//显示购买按钮类型

    private String showPriceFlag;//是否显示价格

    private String showDetialFlag;

    private String showTitleFlag;

    private String showSubTitle;//是否显示副标题

    private String showMethod;//显示类型样式

    private String titleTemplate;//标题模版

    private String wxTitleDate;//标题微信日期

    private String wxTitleAuthor;//标题微信作者

    private String wxTitleLink;//链接标题

    private String wxTitleLinkType;//链接标题类型

    private String wxLinkName;

    private String wxLinkUrl;

    private String heigth;

    private Integer withoutSpace;//图片间隙

    private String bodyTitle;///内容区标题

    private String bodyDesc;//内容区说明

    private String lineType;//辅助线样式

    private String hasPadding;//是否左右留边

    private String background;//背景颜色

    private String color;//字体颜色

    private Integer sort;//排序

    private String isValid;

    private String delFlag;

    private Date dateCreated;

    private Date lastUpdated;

    private Integer size;

    private Integer sizeType;

    private Integer showMethodSize;

    private Integer whiteHeight;

    private Integer goodsNumberType;

    private String tagGroup;

    private String goodsGroup;

    private Integer style;

    private Integer reload;

    private String content;//内容

    private List<AppPagerModuleGood> appPagerModuleGoodList;

    private List<AppModuleExtra> appModuleExtraList;

    public List<AppPagerModuleGood> getAppPagerModuleGoodList() {
        return appPagerModuleGoodList;
    }

    public void setAppPagerModuleGoodList(List<AppPagerModuleGood> appPagerModuleGoodList) {
        this.appPagerModuleGoodList = appPagerModuleGoodList;
    }

    public List<AppModuleExtra> getAppModuleExtraList() {
        return appModuleExtraList;
    }

    public void setAppModuleExtraList(List<AppModuleExtra> appModuleExtraList) {
        this.appModuleExtraList = appModuleExtraList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(String fullScreen) {
        this.fullScreen = fullScreen == null ? null : fullScreen.trim();
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getShowList() {
        return showList;
    }

    public void setShowList(String showList) {
        this.showList = showList == null ? null : showList.trim();
    }

    public String getShowListType() {
        return showListType;
    }

    public void setShowListType(String showListType) {
        this.showListType = showListType == null ? null : showListType.trim();
    }

    public String getBuyBtn() {
        return buyBtn;
    }

    public void setBuyBtn(String buyBtn) {
        this.buyBtn = buyBtn == null ? null : buyBtn.trim();
    }

    public String getBuyBtnType() {
        return buyBtnType;
    }

    public void setBuyBtnType(String buyBtnType) {
        this.buyBtnType = buyBtnType == null ? null : buyBtnType.trim();
    }

    public String getShowPriceFlag() {
        return showPriceFlag;
    }

    public void setShowPriceFlag(String showPriceFlag) {
        this.showPriceFlag = showPriceFlag == null ? null : showPriceFlag.trim();
    }

    public String getShowDetialFlag() {
        return showDetialFlag;
    }

    public void setShowDetialFlag(String showDetialFlag) {
        this.showDetialFlag = showDetialFlag == null ? null : showDetialFlag.trim();
    }

    public String getShowTitleFlag() {
        return showTitleFlag;
    }

    public void setShowTitleFlag(String showTitleFlag) {
        this.showTitleFlag = showTitleFlag == null ? null : showTitleFlag.trim();
    }

    public String getShowSubTitle() {
        return showSubTitle;
    }

    public void setShowSubTitle(String showSubTitle) {
        this.showSubTitle = showSubTitle == null ? null : showSubTitle.trim();
    }

    public String getShowMethod() {
        return showMethod;
    }

    public void setShowMethod(String showMethod) {
        this.showMethod = showMethod == null ? null : showMethod.trim();
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate == null ? null : titleTemplate.trim();
    }

    public String getWxTitleDate() {
        return wxTitleDate;
    }

    public void setWxTitleDate(String wxTitleDate) {
        this.wxTitleDate = wxTitleDate == null ? null : wxTitleDate.trim();
    }

    public String getWxTitleAuthor() {
        return wxTitleAuthor;
    }

    public void setWxTitleAuthor(String wxTitleAuthor) {
        this.wxTitleAuthor = wxTitleAuthor == null ? null : wxTitleAuthor.trim();
    }

    public String getWxTitleLink() {
        return wxTitleLink;
    }

    public void setWxTitleLink(String wxTitleLink) {
        this.wxTitleLink = wxTitleLink == null ? null : wxTitleLink.trim();
    }

    public String getWxTitleLinkType() {
        return wxTitleLinkType;
    }

    public void setWxTitleLinkType(String wxTitleLinkType) {
        this.wxTitleLinkType = wxTitleLinkType == null ? null : wxTitleLinkType.trim();
    }

    public String getWxLinkName() {
        return wxLinkName;
    }

    public void setWxLinkName(String wxLinkName) {
        this.wxLinkName = wxLinkName == null ? null : wxLinkName.trim();
    }

    public String getWxLinkUrl() {
        return wxLinkUrl;
    }

    public void setWxLinkUrl(String wxLinkUrl) {
        this.wxLinkUrl = wxLinkUrl == null ? null : wxLinkUrl.trim();
    }

    public String getHeigth() {
        return heigth;
    }

    public void setHeigth(String heigth) {
        this.heigth = heigth == null ? null : heigth.trim();
    }

    public Integer getWithoutSpace() {
        return withoutSpace;
    }

    public void setWithoutSpace(Integer withoutSpace) {
        this.withoutSpace = withoutSpace;
    }

    public String getBodyTitle() {
        return bodyTitle;
    }

    public void setBodyTitle(String bodyTitle) {
        this.bodyTitle = bodyTitle == null ? null : bodyTitle.trim();
    }

    public String getBodyDesc() {
        return bodyDesc;
    }

    public void setBodyDesc(String bodyDesc) {
        this.bodyDesc = bodyDesc == null ? null : bodyDesc.trim();
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType == null ? null : lineType.trim();
    }

    public String getHasPadding() {
        return hasPadding;
    }

    public void setHasPadding(String hasPadding) {
        this.hasPadding = hasPadding == null ? null : hasPadding.trim();
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background == null ? null : background.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSizeType() {
        return sizeType;
    }

    public void setSizeType(Integer sizeType) {
        this.sizeType = sizeType;
    }

    public Integer getShowMethodSize() {
        return showMethodSize;
    }

    public void setShowMethodSize(Integer showMethodSize) {
        this.showMethodSize = showMethodSize;
    }

    public Integer getWhiteHeight() {
        return whiteHeight;
    }

    public void setWhiteHeight(Integer whiteHeight) {
        this.whiteHeight = whiteHeight;
    }

    public Integer getGoodsNumberType() {
        return goodsNumberType;
    }

    public void setGoodsNumberType(Integer goodsNumberType) {
        this.goodsNumberType = goodsNumberType;
    }

    public String getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(String tagGroup) {
        this.tagGroup = tagGroup == null ? null : tagGroup.trim();
    }

    public String getGoodsGroup() {
        return goodsGroup;
    }

    public void setGoodsGroup(String goodsGroup) {
        this.goodsGroup = goodsGroup == null ? null : goodsGroup.trim();
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getReload() {
        return reload;
    }

    public void setReload(Integer reload) {
        this.reload = reload;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}