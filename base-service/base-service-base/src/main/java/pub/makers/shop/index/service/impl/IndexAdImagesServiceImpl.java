package pub.makers.shop.index.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.index.dao.IndexAdImagesDao;
import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.index.service.IndexAdImagesService;

/**
 * Created by dy on 2017/6/12.
 */
@Service
public class IndexAdImagesServiceImpl extends BaseCRUDServiceImpl<IndexAdImages, String, IndexAdImagesDao> implements IndexAdImagesService {
}
