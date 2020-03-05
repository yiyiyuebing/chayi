package pub.makers.shop.cargo.entity;

import java.io.Serializable;

/**
 * Created by daiwenfa on 2017/7/28.
 */
public class GoodPageTplModelSort implements Serializable {
    private long id;
    private long tplId;
    private long modelId;
    private long goodId;
    private long goodSkuId;
    private int sort;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTplId() {
        return tplId;
    }

    public void setTplId(long tplId) {
        this.tplId = tplId;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public long getGoodId() {
        return goodId;
    }

    public void setGoodId(long goodId) {
        this.goodId = goodId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(long goodSkuId) {
        this.goodSkuId = goodSkuId;
    }
}
