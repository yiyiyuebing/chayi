package pub.makers.shop.index.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.index.entity.IndentMobileModule;
import pub.makers.shop.index.vo.IndexMobileFloorVo;
import pub.makers.shop.purchaseGoods.entity.PurchaseClassify;

import java.util.List;

/**
 * Created by daiwenfa on 2017/7/14.
 */
public interface IndexMobileAdminService {
    ResultList<IndexMobileFloorVo> getIndexMobileFloorPageList(IndentMobileModule indentMobileModule, Paging pg);

    void addOrUpdateIndexMobileFloor(IndentMobileModule param);

    IndentMobileModule getIndentMobileModuleData(String id);

    List<PurchaseClassify> getClassify(String id);

    boolean remove(String id);
}
