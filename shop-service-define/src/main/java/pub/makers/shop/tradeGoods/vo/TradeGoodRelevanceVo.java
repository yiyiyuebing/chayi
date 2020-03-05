package pub.makers.shop.tradeGoods.vo;

import pub.makers.shop.promotion.vo.ManZenAndPresellVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public class TradeGoodRelevanceVo implements Serializable {
    private String id;
    private String name;
    private String smallImage;
    private String isRelevance;
    private String pcAlbumId;
    private String retailPrice;
    private String goodsId;
    private Integer currStock;
    private String skuCode;

    private List<ManZenAndPresellVo> manZenAndPresellVoList;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public List<ManZenAndPresellVo> getManZenAndPresellVoList() {
        return manZenAndPresellVoList;
    }

    public void setManZenAndPresellVoList(List<ManZenAndPresellVo> manZenAndPresellVoList) {
        this.manZenAndPresellVoList = manZenAndPresellVoList;
    }

    public Integer getCurrStock() {
        return currStock;
    }

    public void setCurrStock(Integer currStock) {
        this.currStock = currStock;
    }

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

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getIsRelevance() {
        return isRelevance;
    }

    public void setIsRelevance(String isRelevance) {
        this.isRelevance = isRelevance;
    }

    public String getPcAlbumId() {
        return pcAlbumId;
    }

    public void setPcAlbumId(String pcAlbumId) {
        this.pcAlbumId = pcAlbumId;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
