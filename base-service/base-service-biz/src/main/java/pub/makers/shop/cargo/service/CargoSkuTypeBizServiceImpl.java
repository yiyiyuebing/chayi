package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.entity.CargoSkuType;
import pub.makers.shop.cargo.entity.vo.CargoSkuTypeVo;

import java.util.List;

/**
 * Created by kok on 2017/6/8.
 */
@Service(version = "1.0.0")
public class CargoSkuTypeBizServiceImpl implements CargoSkuTypeBizService {
    @Autowired
    private CargoSkuTypeService cargoSkuTypeService;
    @Autowired
    private CargoSkuItemBizService cargoSkuItemBizService;

    @Override
    public List<CargoSkuTypeVo> getCargoSkuTypeList(String cargoId) {
        List<CargoSkuType> skuTypeList = cargoSkuTypeService.list(Conds.get().eq("cargo_id", cargoId));
        List<CargoSkuTypeVo> skuTypeVoList = Lists.transform(skuTypeList, new Function<CargoSkuType, CargoSkuTypeVo>() {
            @Override
            public CargoSkuTypeVo apply(CargoSkuType type) {
                CargoSkuTypeVo vo = CargoSkuTypeVo.fromCargoSkuType(type);
                vo.setCargoSkuItemList(cargoSkuItemBizService.getCargoSkuItemList(vo.getId()));
                return vo;
            }
        });
        return skuTypeVoList;
    }

    @Override
    public CargoSkuTypeVo getTypeVo(String typeId) {
        CargoSkuType cargoSkuType = cargoSkuTypeService.getById(typeId);
        if (cargoSkuType == null) {
            return new CargoSkuTypeVo();
        }
        CargoSkuTypeVo typeVo = CargoSkuTypeVo.fromCargoSkuType(cargoSkuType);
        return typeVo;
    }
}
