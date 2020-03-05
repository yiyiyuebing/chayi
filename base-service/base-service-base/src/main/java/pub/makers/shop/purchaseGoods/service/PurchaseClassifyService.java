package pub.makers.shop.purchaseGoods.service;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;

import java.util.List;

/**
 * Created by dy on 2017/4/14.
 */
public interface PurchaseClassifyService extends BaseCRUDService<PurchaseClassify> {

    /**
     * 寻找最高级的PurchaseClassify
     * @param id
     * @return
     */
    PurchaseClassify getTopClassifyById(String id);

    /**
     * 通过id集合获取数据
     * @param idList
     * @return
     */
    List<PurchaseClassify> listByIds(List<String> idList);
}
