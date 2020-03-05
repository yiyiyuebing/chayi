package pub.makers.shop.tradeGoods.vo;

import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.tradeGoods.entity.GoodsTheme;

import java.io.Serializable;

/**
 * Created by kok on 2017/5/27.
 */
public class GoodsThemeVo implements Serializable {
    private String id;

    private String name;

    private Integer sort;

    private String showPhoto;

    private String minPhoto;

    private String introPhoto;

    private Integer status;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getShowPhoto() {
        return showPhoto;
    }

    public void setShowPhoto(String showPhoto) {
        this.showPhoto = showPhoto;
    }

    public String getMinPhoto() {
        return minPhoto;
    }

    public void setMinPhoto(String minPhoto) {
        this.minPhoto = minPhoto;
    }

    public String getIntroPhoto() {
        return introPhoto;
    }

    public void setIntroPhoto(String introPhoto) {
        this.introPhoto = introPhoto;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static GoodsThemeVo fromGoodsTheme(GoodsTheme goodsTheme) {
        GoodsThemeVo vo = new GoodsThemeVo();
        vo.setId(goodsTheme.getId().toString());
        vo.setName(goodsTheme.getName());
        vo.setSort(goodsTheme.getSort());
        vo.setShowPhoto(goodsTheme.getShowPhoto());
        vo.setMinPhoto(goodsTheme.getMinPhoto());
        vo.setIntroPhoto(goodsTheme.getIntroPhoto());
        vo.setStatus(goodsTheme.getStatus());
        vo.setCreateBy(goodsTheme.getCreateBy() == null ? null : goodsTheme.getCreateBy().toString());
        vo.setCreateTime(goodsTheme.getCreateTime() == null ? null : DateParseUtil.formatDate(goodsTheme.getCreateTime()));
        vo.setUpdateBy(goodsTheme.getUpdateBy() == null ? null : goodsTheme.getUpdateBy().toString());
        vo.setUpdateTime(goodsTheme.getUpdateTime() == null ? null : DateParseUtil.formatDate(goodsTheme.getUpdateTime()));
        return vo;
    }
}
