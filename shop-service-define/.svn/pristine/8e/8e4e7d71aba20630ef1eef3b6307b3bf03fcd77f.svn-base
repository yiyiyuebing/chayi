package pub.makers.shop.cargo.vo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.cargo.entity.CargoSkuSupplyPrice;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kok on 2017/6/26.
 */
public class CargoSkuSupplyPriceVo implements Serializable {
    private String id;
    private String cargoId;
    private String storeLevelId;
    private String cargoSkuId;
    private String supplyPrice;
    private Integer sectionStart;
    private Integer sectionEnd;
    private Integer startNum;
    private String mulNumFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getStoreLevelId() {
        return storeLevelId;
    }

    public void setStoreLevelId(String storeLevelId) {
        this.storeLevelId = storeLevelId;
    }

    public String getCargoSkuId() {
        return cargoSkuId;
    }

    public void setCargoSkuId(String cargoSkuId) {
        this.cargoSkuId = cargoSkuId;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Integer getSectionStart() {
        return sectionStart;
    }

    public void setSectionStart(Integer sectionStart) {
        this.sectionStart = sectionStart;
    }

    public Integer getSectionEnd() {
        return sectionEnd;
    }

    public void setSectionEnd(Integer sectionEnd) {
        this.sectionEnd = sectionEnd;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public String getMulNumFlag() {
        return mulNumFlag;
    }

    public void setMulNumFlag(String mulNumFlag) {
        this.mulNumFlag = mulNumFlag;
    }

    public static CargoSkuSupplyPriceVo fromCargoSkuSupplyPrice(CargoSkuSupplyPrice price) {
        CargoSkuSupplyPriceVo vo = new CargoSkuSupplyPriceVo();
        vo.setId(price.getId() == null ? null : price.getId().toString());
        vo.setCargoId(price.getCargoId() == null ? null : price.getCargoId().toString());
        vo.setStoreLevelId(price.getStoreLevelId() == null ? null : price.getStoreLevelId().toString());
        vo.setCargoSkuId(price.getCargoSkuId() == null ? null : price.getCargoSkuId().toString());
        vo.setSupplyPrice(price.getSupplyPrice() == null ? BigDecimal.ZERO.toString() : price.getSupplyPrice().toString());
        vo.setSectionStart(price.getSectionStart());
        vo.setSectionEnd(price.getSectionEnd());
        vo.setStartNum(price.getStartNum());
        vo.setMulNumFlag(StringUtils.isEmpty(price.getMulNumFlag()) ? "F" : vo.getMulNumFlag());
        return vo;
    }
}
