package pub.makers.shop.cargo.vo;

import java.io.Serializable;

/**
 * Created by daiwenfa on 2017/7/28.
 */
public class GoodPageTplModelSortVo implements Serializable {
    private String id;
    private String tplId;
    private String modelId;
    private String goodId;
    private String goodSkuId;
    private int sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
