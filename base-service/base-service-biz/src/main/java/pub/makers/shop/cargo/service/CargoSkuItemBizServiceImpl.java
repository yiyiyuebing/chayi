package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.entity.CargoSkuItem;
import pub.makers.shop.cargo.entity.vo.CargoSkuItemVo;

import java.util.List;

/**
 * Created by kok on 2017/6/8.
 */
@Service(version = "1.0.0")
public class CargoSkuItemBizServiceImpl implements CargoSkuItemBizService {
    @Autowired
    private CargoSkuItemService cargoSkuItemService;

    @Override
    public List<CargoSkuItemVo> getCargoSkuItemList(String skuTypeId) {
        List<CargoSkuItem> skuItemList = cargoSkuItemService.list(Conds.get().eq("cargo_sku_type_id", skuTypeId));
        List<CargoSkuItemVo> skuItemVoList = Lists.transform(skuItemList, new Function<CargoSkuItem, CargoSkuItemVo>() {
            @Override
            public CargoSkuItemVo apply(CargoSkuItem item) {
                return CargoSkuItemVo.fromCargoSkuItem(item);
            }
        });
        return skuItemVoList;
    }

    @Override
    public CargoSkuItemVo getItemVo(String itemId) {
        CargoSkuItem cargoSkuItem = cargoSkuItemService.getById(itemId);
        if (cargoSkuItem == null) {
            return new CargoSkuItemVo();
        }
        CargoSkuItemVo itemVo = CargoSkuItemVo.fromCargoSkuItem(cargoSkuItem);
        return itemVo;
    }
}
