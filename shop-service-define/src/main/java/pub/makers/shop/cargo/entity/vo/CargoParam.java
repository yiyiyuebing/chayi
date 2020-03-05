package pub.makers.shop.cargo.entity.vo;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by dy on 2017/5/23.
 */
public class CargoParam implements Serializable {

    private String cargoName;
    private String classifyId;
    private String brandId;


    public String getClassifyId() {
        if (StringUtils.isBlank(this.classifyId) || "0".equals(this.classifyId)) {
            return null;
        }
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getBrandId() {
        if (StringUtils.isBlank(this.brandId) || "0".equals(this.brandId)) {
            return null;
        }
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }
}
