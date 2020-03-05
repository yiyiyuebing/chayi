package pub.makers.shop.purchaseGoods.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.purchaseGoods.dao.PurchaseClassifyDao;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;
import pub.makers.shop.purchaseGoods.service.PurchaseClassifyService;

import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
@Service
public class PurchaseClassifyServiceImpl extends BaseCRUDServiceImpl<PurchaseClassify, String, PurchaseClassifyDao> implements PurchaseClassifyService {

    @Override
    public PurchaseClassify getTopClassifyById(String id) {
        PurchaseClassify pv = dao.getById(Long.valueOf(id));
        if (pv == null) {
            return null;
        }
        if ("1".equals(pv.getParentId())) {
            return pv;
        } else {
            return this.getTopClassifyById(pv.getParentId());
        }

    }

    @Override
    public List<PurchaseClassify> listByIds(List<String> idList) {
        return list(Conds.get().in("id", idList));
    }
}
