package pub.makers.shop.cargo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.cargo.entity.CargoSku;

/**
 * Created by dy on 2017/6/24.
 */
@Service(version = "1.0.0")
public class CargoSkuBizServiceImpl implements CargoSkuBizService {

    @Autowired
    private CargoSkuService cargoSkuService;

    @Override
    public CargoSku getCargoSkuById(String id) {
        return cargoSkuService.getById(id);
    }
}
