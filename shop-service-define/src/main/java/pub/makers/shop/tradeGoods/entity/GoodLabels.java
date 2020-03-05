package pub.makers.shop.tradeGoods.entity;

import java.io.Serializable;

/**
 * Created by dy on 2017/5/25.
 */
public class GoodLabels implements Serializable {

    private Long id;
    private Long goodId;
    private Long goodBaseLabelId;
    private String goodType;

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodBaseLabelId() {
        return goodBaseLabelId;
    }

    public void setGoodBaseLabelId(Long goodBaseLabelId) {
        this.goodBaseLabelId = goodBaseLabelId;
    }
}
