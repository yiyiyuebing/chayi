package pub.makers.shop.index.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.index.dao.IndexFloorKeywordDao;
import pub.makers.shop.index.entity.IndexFloorKeyword;
import pub.makers.shop.index.service.IndexFloorKeywordService;

/**
 * Created by dy on 2017/6/12.
 */
@Service
public class IndexFloorKeywordServiceImpl extends BaseCRUDServiceImpl<IndexFloorKeyword, String, IndexFloorKeywordDao> implements IndexFloorKeywordService {
}
