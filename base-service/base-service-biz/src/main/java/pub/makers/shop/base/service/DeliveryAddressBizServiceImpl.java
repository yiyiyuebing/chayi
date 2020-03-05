package pub.makers.shop.base.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.entity.DeliveryAddress;

import java.util.List;

/**
 * Created by dy on 2017/7/17.
 */
@Service(version = "1.0.0")
public class DeliveryAddressBizServiceImpl implements DeliveryAddressBizService {
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Override
    public DeliveryAddress exchangeAddressInfo() {
        List<DeliveryAddress> deliveryAddresses = deliveryAddressService.list(Conds.get().order("create_time desc"));
        if (deliveryAddresses.isEmpty()) {
            return null;
        }
        return deliveryAddresses.get(0);
    }
}
