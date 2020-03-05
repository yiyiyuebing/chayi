package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.cargo.entity.CargoBaseSkuType;
import pub.makers.shop.cargo.entity.vo.CargoBaseSkuTypeVo;

/**
 * Created by kok on 2017/6/15.
 */
@Service(version = "1.0.0")
public class CargoBaseSkuTypeBizServiceImpl implements CargoBaseSkuTypeBizService {
    @Autowired
    private CargoBaseSkuTypeService cargoBaseSkuTypeService;

    @Override
    public CargoBaseSkuTypeVo getTypeVo(String typeId) {
        CargoBaseSkuType cargoBaseSkuType = cargoBaseSkuTypeService.getById(typeId);
        CargoBaseSkuTypeVo typeVo = new CargoBaseSkuTypeVo();
        BeanUtils.copyProperties(cargoBaseSkuType, typeVo);
        return typeVo;
    }
}
