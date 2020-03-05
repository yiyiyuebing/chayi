package pub.makers.shop.purchaseGoods.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.lantu.base.common.entity.BoolType;
import org.springframework.beans.factory.annotation.Autowired;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.purchaseGoods.entity.PurchaseSearchKeyword;

import java.util.List;

/**
 * Created by kok on 2017/6/23.
 */
@Service(version = "1.0.0")
public class PurchaseSearchKeywordBizServiceImpl implements PurchaseSearchKeywordBizService {
    @Autowired
    private PurchaseSearchKeywordService purchaseSearchKeywordService;

    @Override
    public List<PurchaseSearchKeyword> getKeywordList(Integer limit, IndexAdPlatform platform) {
        ValidateUtils.notNull(platform, "平台类型为空");
        ValidateUtils.notNull(limit, "数量为空");
        List<PurchaseSearchKeyword> keywordList = purchaseSearchKeywordService.list(Conds.get().eq("platform", platform.name())
                .eq("is_valid", BoolType.T.name()).eq("del_flag", BoolType.F.name()).order("sort desc").page(0, limit));
        return keywordList;
    }
}
