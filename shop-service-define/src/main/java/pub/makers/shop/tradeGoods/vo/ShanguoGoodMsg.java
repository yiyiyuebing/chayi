/**
 * 
 */
package pub.makers.shop.tradeGoods.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 三国的商品列表对象
 * 
 * @author wqh
 *
 * @add by 2016-05-17
 */
public class ShanguoGoodMsg implements Serializable {
	private String id;
	private String goodName;
	private String goodDesc;
	private Date createTime;
	private Integer saleNum;
	private Integer saleAllNum;
	private String showPicture;
	private Double marketPrice;
	private Double salePrice;
	private Double supplyPrice;
	private Integer stock;
	private Integer storeNum;
	private String shopId;
	private Date startDate;
	private Date endDate;
	private String columnName;
	private String cargoId;
	private String post;
	private String postId; // 配送规则
    private String postName;//包邮名称
    private String min_photo;//缩略图
    private String cinvcode_parent;//父项编码
    private String detailPhoto;
    private String packSpec;//套餐规格
    private Integer minTuanNum;

    public String getPackSpec() {
        return packSpec;
    }

    public void setPackSpec(String packSpec) {
        this.packSpec = packSpec;
    }

    public String getCinvcode_parent() {
        return cinvcode_parent;
    }

    public void setCinvcode_parent(String cinvcode_parent) {
        this.cinvcode_parent = cinvcode_parent;
    }

    public String getMin_photo() {
        return min_photo;
    }

    public void setMin_photo(String min_photo) {
        this.min_photo = min_photo;
    }

    public Integer getSaleAllNum() {
        return saleAllNum;
    }

    public void setSaleAllNum(Integer saleAllNum) {
        this.saleAllNum = saleAllNum;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodDesc() {
		return goodDesc;
	}

	public void setGoodDesc(String goodDesc) {
		this.goodDesc = goodDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getShowPicture() {
		return showPicture;
	}

	public void setShowPicture(String showPicture) {
		this.showPicture = showPicture;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getCargoId() {
		return cargoId;
	}

	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Double getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(Double supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDetailPhoto() {
        return detailPhoto;
    }

    public void setDetailPhoto(String detailPhoto) {
        this.detailPhoto = detailPhoto;
    }

	public Integer getMinTuanNum() {
		return minTuanNum;
	}

	public void setMinTuanNum(Integer minTuanNum) {
		this.minTuanNum = minTuanNum;
	}
    
}
