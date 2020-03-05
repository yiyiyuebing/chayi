package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.cargo.entity.CargoBaseSkuItem;
import pub.makers.shop.cargo.entity.vo.CargoBaseSkuItemVo;

/**
 * Created by kok on 2017/6/16.
 */
@Service(version = "1.0.0")
public class CargoBaseSkuItemBizServiceImpl implements CargoBaseSkuItemBizService {
    @Autowired
    private CargoBaseSkuItemService cargoBaseSkuItemService;

    @Override
    public CargoBaseSkuItemVo getItemVo(String itemId) {
        CargoBaseSkuItem cargoBaseSkuItem = cargoBaseSkuItemService.getById(itemId);
        CargoBaseSkuItemVo itemVo = new CargoBaseSkuItemVo();
        BeanUtils.copyProperties(cargoBaseSkuItem, itemVo);
        return itemVo;
    }
}
