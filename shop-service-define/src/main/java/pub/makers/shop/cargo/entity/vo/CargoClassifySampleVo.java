package pub.makers.shop.cargo.entity.vo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.cargo.entity.CargoClassify;

import java.io.Serializable;

/**
 * Created by dy on 2017/5/24.
 */
public class CargoClassifySampleVo implements Serializable {

    private String id;
    private String name;

    private Long parentId;

    public CargoClassifySampleVo() {
    }

    public CargoClassifySampleVo(CargoClassify cargoClassify) {
        if (cargoClassify.getId() != null) {
            this.id = cargoClassify.getId() + "";
        }
        if (StringUtils.isNotBlank(cargoClassify.getName())) {
            this.name = cargoClassify.getName();
        }
        if (cargoClassify.getParentId() != null) {
            this.parentId = cargoClassify.getParentId();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
