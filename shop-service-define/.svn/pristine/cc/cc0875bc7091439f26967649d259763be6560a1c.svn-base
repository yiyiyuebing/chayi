package pub.makers.shop.cargo.service;

import pub.makers.shop.cargo.entity.CargoBaseSkuItem;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/22.
 */
public interface CargoBaseSkuItemMgrBizService {

    /**
     * 获取SKU规格项数据
     * @param skuTypeId
     * @param type
     * @param searchName
     * @return
     */
    List<CargoBaseSkuItem> selectSkuItemBySkuTypeId(Long skuTypeId, String type, String searchName);

    /**
     * 新增SKU规格项数据
     * @param skuTypeId
     * @param name
     * @return
     */
    Map<String,Object> addSkuItemBySkuTypeId(Long skuTypeId, String name);
}
