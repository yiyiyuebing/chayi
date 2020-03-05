package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.shop.tradeGoods.entity.TradeHeadStore;

/**
 * Created by dy on 2017/6/24.
 */
@Service(version = "1.0.0")
public class TradeHeadStoreBizServiceImpl implements TradeHeadStoreBizService {

    @Autowired
    private TradeHeadStoreService tradeHeadStoreService;

    @Override
    public TradeHeadStore getById(String id) {
        return tradeHeadStoreService.getById(id);
    }
}
