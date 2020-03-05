package pub.makers.shop.browseLog.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.browseLog.dao.TradeGoodsBrowseLogDao;
import pub.makers.shop.browseLog.entity.GoodsBrowseLog;
import pub.makers.shop.browseLog.service.GoodsBrowseLogService;

/**
 * Created by kok on 2017/6/23.
 */
@Service
public class TradeGoodsBrowseLogServiceImpl extends BaseCRUDServiceImpl<GoodsBrowseLog, String, TradeGoodsBrowseLogDao> implements GoodsBrowseLogService {
}
