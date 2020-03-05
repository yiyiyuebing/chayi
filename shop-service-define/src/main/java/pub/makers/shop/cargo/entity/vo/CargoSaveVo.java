package pub.makers.shop.cargo.entity.vo;

import pub.makers.shop.cargo.entity.CargoBasePropertys;
import pub.makers.shop.tradeGoods.vo.TradeGiftRuleVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dy on 2017/5/23.
 */
public class CargoSaveVo implements Serializable {

    private Long id;
    private Long brandId;
    private Long supplierId;
    private String name;
    private String cargoNo;
    private Long classifyId;
    private Long parentClassifyId;
    private Long freightTplId;
    private String mobileDetailInfo;
    private String pcDetailInfo;
    private String volume;
    private String weight;
    private String isSancha;
    private String afterSell;
    private String cargoType;

    private List<CargoBasePropertys> cargoBasePropertys;
    private List<TradeGiftRuleVo> giftGoods;

    private ImageGroupVo mobileSwiperImgs;
    private ImageGroupVo pcSwiperImgs;

    private List<Long> skuDelete;
    private List<CargoSkuVo> skuChange;
    private List<CargoSkuVo> skuAdd;
    private List<CargoSkuTypeSaveVo> skuTypes;
    private String weightUnit;
    private String volumeUnit;

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public String getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public String getAfterSell() {
        return afterSell;
    }

    public void setAfterSell(String afterSell) {
        this.afterSell = afterSell;
    }

    public String getIsSancha() {
        return isSancha;
    }

    public void setIsSancha(String isSancha) {
        this.isSancha = isSancha;
    }

    public Long getParentClassifyId() {
        return parentClassifyId;
    }

    public void setParentClassifyId(Long parentClassifyId) {
        this.parentClassifyId = parentClassifyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCargoNo() {
        return cargoNo;
    }

    public void setCargoNo(String cargoNo) {
        this.cargoNo = cargoNo;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getFreightTplId() {
        return freightTplId;
    }

    public void setFreightTplId(Long freightTplId) {
        this.freightTplId = freightTplId;
    }

    public String getMobileDetailInfo() {
        return mobileDetailInfo;
    }

    public void setMobileDetailInfo(String mobileDetailInfo) {
        this.mobileDetailInfo = mobileDetailInfo;
    }

    public String getPcDetailInfo() {
        return pcDetailInfo;
    }

    public void setPcDetailInfo(String pcDetailInfo) {
        this.pcDetailInfo = pcDetailInfo;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<CargoBasePropertys> getCargoBasePropertys() {
        return cargoBasePropertys;
    }

    public void setCargoBasePropertys(List<CargoBasePropertys> cargoBasePropertys) {
        this.cargoBasePropertys = cargoBasePropertys;
    }

    public List<TradeGiftRuleVo> getGiftGoods() {
        return giftGoods;
    }

    public void setGiftGoods(List<TradeGiftRuleVo> giftGoods) {
        this.giftGoods = giftGoods;
    }

    public ImageGroupVo getMobileSwiperImgs() {
        return mobileSwiperImgs;
    }

    public void setMobileSwiperImgs(ImageGroupVo mobileSwiperImgs) {
        this.mobileSwiperImgs = mobileSwiperImgs;
    }

    public ImageGroupVo getPcSwiperImgs() {
        return pcSwiperImgs;
    }

    public void setPcSwiperImgs(ImageGroupVo pcSwiperImgs) {
        this.pcSwiperImgs = pcSwiperImgs;
    }

    public List<Long> getSkuDelete() {
        return skuDelete;
    }

    public void setSkuDelete(List<Long> skuDelete) {
        this.skuDelete = skuDelete;
    }

    public List<CargoSkuVo> getSkuChange() {
        return skuChange;
    }

    public void setSkuChange(List<CargoSkuVo> skuChange) {
        this.skuChange = skuChange;
    }

    public List<CargoSkuVo> getSkuAdd() {
        return skuAdd;
    }

    public void setSkuAdd(List<CargoSkuVo> skuAdd) {
        this.skuAdd = skuAdd;
    }

    public List<CargoSkuTypeSaveVo> getSkuTypes() {
        return skuTypes;
    }

    public void setSkuTypes(List<CargoSkuTypeSaveVo> skuTypes) {
        this.skuTypes = skuTypes;
    }
}
