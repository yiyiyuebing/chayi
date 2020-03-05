package pub.makers.shop.tradeGoods.vo;

import pub.makers.shop.promotion.vo.GoodPromotionalInfoVo;
import pub.makers.shop.store.vo.ImageVo;
import pub.makers.shop.tradeGoods.entity.TradeGoodSku;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeGoodSkuVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -3188105058138539098L;

	private int leftNums;

	private String skuLong;

	private String id;

    private String goodId;

    private String cargoSkuId;

    private Long nums=0l;

    private BigDecimal marketPrice;

    private BigDecimal retailPrice;

    private BigDecimal fixedPrice;

    private Date startTime;

    private String startTimeStr;

    private Date endTime;

    private Integer limitNum=0;

    private BigDecimal salePrice;

    private String cargoSkuName;

    private Integer saleNum=0;

    private String goodName;

    private String code;

    private Integer u8Nums;

    private Integer onSalesNo=0;

    private Integer cargoSkuStock;

    private Integer stock;

    private Integer onPayNo;

    private String tplId;

    /** 最小团购数量 */
	private Integer minTuanNum = 0;

    private String skuValue;

    private GoodPromotionalInfoVo promotionalInfo;

    private String supplyPrice;

    private ImageVo coverImg;

    public int getLeftNums() {
        return leftNums;
    }

    public void setLeftNums(int leftNums) {
        this.leftNums = leftNums;
    }

    public String getTplId() {
        return tplId;
    }

    public void setTplId(String tplId) {
        this.tplId = tplId;
    }

    public Integer getMinTuanNum() {
		return minTuanNum;
	}

	public void setMinTuanNum(Integer minTuanNum) {
		this.minTuanNum = minTuanNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getCargoSkuId() {
        return cargoSkuId;
    }

    public void setCargoSkuId(String cargoSkuId) {
        this.cargoSkuId = cargoSkuId;
    }

    public Long getNums() {
        return nums;
    }

    public void setNums(Long nums) {
        this.nums = nums;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getCargoSkuName() {
        return cargoSkuName;
    }

    public void setCargoSkuName(String cargoSkuName) {
        this.cargoSkuName = cargoSkuName;
    }

	public String getSkuLong() {
		return skuLong;
	}

	public void setSkuLong(String skuLong) {
		this.skuLong = skuLong;
	}

    public Integer getU8Nums() {
        return u8Nums;
    }

    public void setU8Nums(Integer u8Nums) {
        this.u8Nums = u8Nums;
    }

    public Integer getOnSalesNo() {
        return onSalesNo;
    }

    public void setOnSalesNo(Integer onSalesNo) {
        this.onSalesNo = onSalesNo;
    }

    public Integer getOnPayNo() {
        return onPayNo;
    }

    public void setOnPayNo(Integer onPayNo) {
        this.onPayNo = onPayNo;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

    public GoodPromotionalInfoVo getPromotionalInfo() {
        return promotionalInfo;
    }

    public void setPromotionalInfo(GoodPromotionalInfoVo promotionalInfo) {
        this.promotionalInfo = promotionalInfo;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Integer getCargoSkuStock() {
        return cargoSkuStock;
    }

    public void setCargoSkuStock(Integer cargoSkuStock) {
        this.cargoSkuStock = cargoSkuStock;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public ImageVo getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(ImageVo coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public static TradeGoodSkuVo fromTradeGoodSku(TradeGoodSku sku) {
        TradeGoodSkuVo vo = new TradeGoodSkuVo();
        vo.setId(sku.getId() == null ? null : sku.getId().toString());
        vo.setGoodId(sku.getGoodId() == null ? null : sku.getGoodId().toString());
        vo.setCargoSkuId(sku.getCargoSkuId() == null ? null : sku.getCargoSkuId().toString());
        vo.setCargoSkuName(sku.getCargoSkuName());
        vo.setNums(sku.getNums());
        vo.setMarketPrice(sku.getMarketPrice());
        vo.setRetailPrice(sku.getRetailPrice());
        vo.setStartTime(sku.getStartTime());
        vo.setEndTime(sku.getEndTime());
        vo.setLimitNum(sku.getLimitNum() == null ? 0 : sku.getLimitNum());
        vo.setSalePrice(sku.getSalePrice() == null ? BigDecimal.ZERO : sku.getSalePrice());
        vo.setSaleNum(sku.getSaleNum() == null ? 0 : sku.getSaleNum());
        vo.setMinTuanNum(sku.getMinTuanNum() == null ? 0 : sku.getMinTuanNum());
        vo.setOnSalesNo(sku.getOnSalesNo() == null ? 0 : sku.getOnSalesNo());
        vo.setOnPayNo(sku.getOnPayNo() == null ? 0 : sku.getOnPayNo());
        return vo;
    }
}