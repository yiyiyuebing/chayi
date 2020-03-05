package pub.makers.shop.tradeGoods.vo;


import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.tradeGoods.entity.TradeGood;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: GoodDetailsVo.java
 * @Package com.club.web.store.vo
 * @Description: 商品详情VO
 * @author hqLin
 * @date 2016/04/19
 * @version V1.0
 */
public class GoodDetailsVo implements Serializable{

	private String tradeGoodId; // 商品ID
	private List<TradeGoodSkuVo> goodSkuList; // 商品SKU
	private String name; // 商品名称
	private String packSpec; // 套餐规格

	private String post; // 包邮
	private BigDecimal marketPrice; // 市场价
	private BigDecimal salePrice; // 零售价
	private BigDecimal supplyPrice; // 零售价
	private Integer saleNum; // 销量
	private Integer saleAllNum; // 总销量
	private Integer kucun; // 库存
	private Integer score; // 评价平均分
	private String smallImage; // 头像
	private ImageGroupVo showImages; // 展示图片组
    private String showPhoto;
    private String detailPhoto;
	private ImageGroupVo detailImages; // 详情图片组
	private List<GoodEvaluationVo> goodEvaluationList; // 商品评价
	private List<Map<String, Object>> skuTypeList; // 商品SKU类型列表
	private Integer status;
	private Date startDate;
	private Date endDate;
	private String columnName;
	private Integer storeNum;
	private String postId; // 配送规则
    private String postName; //包邮名称
	private String cargoNo;//货品编号
	private Integer minTuanNum;//最小团购数

    public String getShowPhoto() {
        return showPhoto;
    }

    public void setShowPhoto(String showPhoto) {
        this.showPhoto = showPhoto;
    }

    public String getDetailPhoto() {
        return detailPhoto;
    }

    public void setDetailPhoto(String detailPhoto) {
        this.detailPhoto = detailPhoto;
    }

    public String getPackSpec() {
        return packSpec;
    }

    public void setPackSpec(String packSpec) {
        this.packSpec = packSpec;
    }

    public Integer getSaleAllNum() {
        return saleAllNum;
    }

    public void setSaleAllNum(Integer saleAllNum) {
        this.saleAllNum = saleAllNum;
    }

    public String getTradeGoodId() {
		return tradeGoodId;
	}

	public void setTradeGoodId(String tradeGoodId) {
		this.tradeGoodId = tradeGoodId;
	}

	public List<TradeGoodSkuVo> getGoodSkuList() {
		return goodSkuList;
	}

	public void setGoodSkuList(List<TradeGoodSkuVo> goodSkuList) {
		this.goodSkuList = goodSkuList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getKucun() {
		return kucun;
	}

	public void setKucun(Integer kucun) {
		this.kucun = kucun;
	}

	public ImageGroupVo getShowImages() {
		return showImages;
	}

	public void setShowImages(ImageGroupVo showImages) {
		this.showImages = showImages;
	}

	public ImageGroupVo getDetailImages() {
		return detailImages;
	}

	public void setDetailImages(ImageGroupVo detailImages) {
		this.detailImages = detailImages;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public List<GoodEvaluationVo> getGoodEvaluationList() {
		return goodEvaluationList;
	}

	public void setGoodEvaluationList(List<GoodEvaluationVo> goodEvaluationList) {
		this.goodEvaluationList = goodEvaluationList;
	}

	public List<Map<String, Object>> getSkuTypeList() {
		return skuTypeList;
	}

	public void setSkuTypeList(List<Map<String, Object>> skuTypeList) {
		this.skuTypeList = skuTypeList;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getStoreNum() {
		return storeNum;
	}

	public void setStoreNum(Integer storeNum) {
		this.storeNum = storeNum;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getCargoNo() {
		return cargoNo;
	}

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	public Integer getMinTuanNum() {
		return minTuanNum;
	}

	public void setMinTuanNum(Integer minTuanNum) {
		this.minTuanNum = minTuanNum;
	}

	public static GoodDetailsVo fromTradeGood(TradeGood good) {
		GoodDetailsVo detailsVo = new GoodDetailsVo();
		detailsVo.setTradeGoodId(good.getId().toString());
		detailsVo.setName(good.getName());
		return detailsVo;
	}
    
}