package pub.makers.shop.store.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.store.dao.VtwoStorePhotoDao;
import pub.makers.shop.store.entity.VtwoStorePhoto;
import pub.makers.shop.store.service.VtwoStorePhotoService;

/**
 * Created by kok on 2017/6/6.
 */
@Service
public class VtwoStorePhotoServiceImpl extends BaseCRUDServiceImpl<VtwoStorePhoto, String, VtwoStorePhotoDao> implements VtwoStorePhotoService {
}
