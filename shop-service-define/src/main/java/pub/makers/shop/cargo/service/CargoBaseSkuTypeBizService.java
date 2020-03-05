package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.entity.vo.CargoBaseSkuTypeVo;

/**
 * Created by kok on 2017/6/15.
 */
public interface CargoBaseSkuTypeBizService {
    /**
     * 规格类型信息
     */
    CargoBaseSkuTypeVo getTypeVo(String typeId);
}
