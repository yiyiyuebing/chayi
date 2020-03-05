package pub.makers.shop.cargo.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.cargo.vo.CargoSkuStockParams;
import pub.makers.shop.cargo.vo.CargoSkuStockVo;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/5/23.
 */
public interface CargoSkuStockMgrBizService {

    ResultList<CargoSkuStockVo> listByCondition(CargoSkuStockParams param, Paging pg);

    void setWarnings(CargoSkuStockParams param, String ids);

    void setWarningsByIds(CargoSkuStockParams param);

    void updateIsSync(CargoSkuStockParams param);

    void updateAutoSynchU8Stock(String skuIdListStr, Integer synchStatus);

    List<CargoSkuStockVo> stockDetail(String id);

    /**
     * 从U8中同步库存
     * @param cInvCodes
     */
    void updateStockForU8(String cInvCodes) throws Exception ;
}
