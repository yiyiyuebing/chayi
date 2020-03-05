package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.entity.vo.CargoSkuTypeVo;

import java.util.List;

/**
 * Created by kok on 2017/6/8.
 */
public interface CargoSkuTypeBizService {
    /**
     * 获取货品sku规格
     */
    List<CargoSkuTypeVo> getCargoSkuTypeList(String cargoId);

    /**
     * 规格类型信息
     */
    CargoSkuTypeVo getTypeVo(String typeId);
}
