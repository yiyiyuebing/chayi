package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.entity.vo.CargoBaseSkuItemVo;

/**
 * Created by kok on 2017/6/16.
 */
public interface CargoBaseSkuItemBizService {
    /**
     * 规格元素信息
     */
    CargoBaseSkuItemVo getItemVo(String itemId);
}
