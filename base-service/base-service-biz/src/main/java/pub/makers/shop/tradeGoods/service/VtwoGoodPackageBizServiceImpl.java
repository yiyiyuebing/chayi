package pub.makers.shop.tradeGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.store.entity.VtwoGoodPackage;
import pub.makers.shop.store.service.VtwoGoodPackageBizService;

/**
 * Created by dy on 2017/6/24.
 */
@Service(version = "1.0.0")
public class VtwoGoodPackageBizServiceImpl implements VtwoGoodPackageBizService {

    @Autowired
    private VtwoGoodPackageService vtwoGoodPackageService;

    @Override
    public VtwoGoodPackage getGoodPackageByBoomId(String boomId) {
        return vtwoGoodPackageService.get(Conds.get().eq("boom_id", boomId));
    }
}
