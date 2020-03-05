package pub.makers.shop.purchaseGoods.pojo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.base.enums.ClientType;
import pub.makers.shop.baseGood.vo.BaseGoodVo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kok on 2017/6/1.
 */
public class PurchaseGoodsQuery implements Serializable {
    private String storeLevelId;
    private String keyword;
    private String keywordClassifyIds;
    private String purGoodsName;
    private List<String> classifyList = new ArrayList<String>();
    private List<String> keywordClassifyList = new ArrayList<String>();
    private List<String> brandList = new ArrayList<String>();
    private List<String> classifyAttrList = new ArrayList<String>();
    private String classifyIds;
    private String classifyId;
    private String brandIds;
    private String classifyAttrIds;
    private String minPrice;
    private String maxPrice;
    private String priceSort;
    private String saleNumSort;
    private String createTimeSort;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderIndex;
    private ClientType clientType;

    private Integer totalRecods;
    private Integer totalPageNo;
    private List<BaseGoodVo> purchaseGoodsVos;

    private String searchEv;




    public PurchaseGoodsQuery() {
    }

    public void bulidPurchaseGoodsQuery(HttpServletRequest request) {
        if (StringUtils.isNotEmpty(request.getParameter("pageNum"))) {
            this.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
        } else {
            this.setPageNum(1);
        }
        if (StringUtils.isNotEmpty(request.getParameter("pageSize"))) {
            this.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        } else {
            this.setPageSize(32);
        }

        if (StringUtils.isNotEmpty(request.getParameter("storeLevelId"))) {
            this.setStoreLevelId(request.getParameter("storeLevelId").toString());
        }

        if (StringUtils.isNotEmpty(request.getParameter("purGoodsName"))) {
            this.setPurGoodsName(request.getParameter("purGoodsName").toString());
        }
        if (StringUtils.isNotEmpty(request.getParameter("minPrice"))) {
            this.setMinPrice(request.getParameter("minPrice").toString());
        }
        if (StringUtils.isNotEmpty(request.getParameter("maxPrice"))) {
            this.setMaxPrice(request.getParameter("maxPrice").toString());
        }
        if (StringUtils.isNotEmpty(request.getParameter("priceSort"))) {
            this.setPriceSort(request.getParameter("priceSort").toString());
        }
        if (StringUtils.isNotEmpty(request.getParameter("saleNumSort"))) {
            this.setSaleNumSort(request.getParameter("saleNumSort").toString());
        }
        if (StringUtils.isNotEmpty(request.getParameter("createTimeSort"))) {
            this.setCreateTimeSort(request.getParameter("createTimeSort").toString());
        }
        if (request.getParameter("priceSort") == null && request.getParameter("saleNumSort") == null && request.getParameter("createTimeSort") == null) {
            this.setOrderIndex("0");
        }

        if (StringUtils.isNotEmpty(request.getParameter("orderIndex"))) {
            this.setOrderIndex(request.getParameter("orderIndex").toString());
        }

        if (StringUtils.isNotEmpty(request.getParameter("classifyIds"))) {
            String classifyIdsStr = request.getParameter("classifyIds").toString();
            this.classifyList = Arrays.asList(classifyIdsStr.split(","));
        }

        if (StringUtils.isNotEmpty(request.getParameter("classifyIds"))) {
            this.classifyIds = request.getParameter("classifyIds").toString();
        }

        if (StringUtils.isNotEmpty(request.getParameter("classifyId"))) {
            this.classifyId = request.getParameter("classifyId").toString();
        }

        if (StringUtils.isNotEmpty(request.getParameter("brandIds"))) {
            this.brandIds = request.getParameter("brandIds").toString();
        }

        if (StringUtils.isNotEmpty(request.getParameter("searchEv"))) {
            this.searchEv = request.getParameter("searchEv").toString();
            String[] attrTypes = this.searchEv.split("\\|");
            String classifyAttrId = "";
            for (String attrType : attrTypes) {
                classifyAttrId += attrType.substring(attrType.indexOf(":") + 1, attrType.length()) + ",";
            }
            if (classifyAttrId.length() > 0) {
                this.classifyAttrIds = classifyAttrId.substring(0, classifyAttrId.length() - 1);
            }
        }
        if (StringUtils.isNotEmpty(request.getParameter("keyword"))) {
            this.keyword = request.getParameter("keyword").toString();
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordClassifyIds() {
        return keywordClassifyIds;
    }

    public void setKeywordClassifyIds(String keywordClassifyIds) {
        this.keywordClassifyIds = keywordClassifyIds;
    }

    public String getSearchEv() {
        return searchEv;
    }

    public void setSearchEv(String searchEv) {
        this.searchEv = searchEv;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public List<BaseGoodVo> getPurchaseGoodsVos() {
        return purchaseGoodsVos;
    }

    public void setPurchaseGoodsVos(List<BaseGoodVo> purchaseGoodsVos) {
        this.purchaseGoodsVos = purchaseGoodsVos;
    }

    public Integer getTotalPageNo() {
        if (this.totalRecods != null) {
            return this.totalRecods % this.pageSize > 0 ?  (this.totalRecods / this.pageSize) + 1 :  this.totalRecods / this.pageSize;
        }
        return totalPageNo;
    }

    public void setTotalPageNo(Integer totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

    public String getStoreLevelId() {
        return storeLevelId;
    }

    public void setStoreLevelId(String storeLevelId) {
        this.storeLevelId = storeLevelId;
    }

    public String getPurGoodsName() {
        return purGoodsName;
    }

    public void setPurGoodsName(String purGoodsName) {
        this.purGoodsName = purGoodsName;
    }

    public List<String> getClassifyList() {
        return classifyList;
    }

    public void setClassifyList(List<String> classifyList) {
        this.classifyList = classifyList;
    }

    public List<String> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<String> brandList) {
        this.brandList = brandList;
    }

    public List<String> getClassifyAttrList() {
        return classifyAttrList;
    }

    public void setClassifyAttrList(List<String> classifyAttrList) {
        this.classifyAttrList = classifyAttrList;
    }

    public String getClassifyIds() {
        return classifyIds;
    }

    public void setClassifyIds(String classifyIds) {
        this.classifyIds = classifyIds;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getClassifyAttrIds() {
        return classifyAttrIds;
    }

    public void setClassifyAttrIds(String classifyAttrIds) {
        this.classifyAttrIds = classifyAttrIds;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(String priceSort) {
        this.priceSort = priceSort;
    }

    public String getSaleNumSort() {
        return saleNumSort;
    }

    public void setSaleNumSort(String saleNumSort) {
        this.saleNumSort = saleNumSort;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecods() {
        return totalRecods;
    }

    public void setTotalRecods(Integer totalRecods) {
        this.totalRecods = totalRecods;
    }

    public String getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(String orderIndex) {
        this.orderIndex = orderIndex;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getCreateTimeSort() {
        return createTimeSort;
    }

    public void setCreateTimeSort(String createTimeSort) {
        this.createTimeSort = createTimeSort;
    }

    public List<String> getKeywordClassifyList() {
        return keywordClassifyList;
    }

    public void setKeywordClassifyList(List<String> keywordClassifyList) {
        this.keywordClassifyList = keywordClassifyList;
    }
}
