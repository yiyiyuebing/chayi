package pub.makers.shop.tradeGoods.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by kok on 2017/5/26.
 */
public class GoodSearchInfo implements Serializable {
    private Map<String, Object> conditionStr;
    private String storeLevelId;
    private String shopId;
    private String startPrice;
    private String endPrice;
    private String brandId;
    private String classifyId;
    private String goodName;
    private String oneType;
    private String twoType;
    private String threeType;
    private String use;
    private String columnId;
    private String saleNumSort;
    private String priceSort;
    private int pageSize = 6;
    private int pageNum = 1;
    private String category;
    private String theme;

    public String getStoreLevelId() {
        return storeLevelId;
    }

    public void setStoreLevelId(String storeLevelId) {
        this.storeLevelId = storeLevelId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Map<String, Object> getConditionStr() {
        return conditionStr;
    }

    public void setConditionStr(Map<String, Object> conditionStr) {
        this.conditionStr = conditionStr;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }

    public String getTwoType() {
        return twoType;
    }

    public void setTwoType(String twoType) {
        this.twoType = twoType;
    }

    public String getThreeType() {
        return threeType;
    }

    public void setThreeType(String threeType) {
        this.threeType = threeType;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getSaleNumSort() {
        return saleNumSort;
    }

    public void setSaleNumSort(String saleNumSort) {
        this.saleNumSort = saleNumSort;
    }

    public String getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(String priceSort) {
        this.priceSort = priceSort;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
