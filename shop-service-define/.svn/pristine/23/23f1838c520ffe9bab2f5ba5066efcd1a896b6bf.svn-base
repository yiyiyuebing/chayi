package pub.makers.shop.promotion.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.promotion.entity.SaleActivity;
import pub.makers.shop.promotion.pojo.SaleActivityGoodPram;
import pub.makers.shop.promotion.vo.PresellGoodVo;
import pub.makers.shop.promotion.vo.PresellParam;
import pub.makers.shop.promotion.vo.SaleActivityGoodVo;

import java.util.Map;

/**   modules/promotion/view/specialPurchaseSaleActivity.js
 * Created by devpc on 2017/8/18.
 */
public interface SaleActivityGoodBizService {


    ResultList<SaleActivityGoodVo> getSaleActivityGoodsList(SaleActivityGoodPram saleActivityGoodPram  ,Paging pg);

    Map<String,Object> updateIsValue(SaleActivityGoodPram saleActivityGoodPram);//禁用或启用

    Map<String,Object> updateOrAddSaleActivityGood(SaleActivity saleActivity ,String userId );

    Map<String,Object> delSaleActivityGood(SaleActivityGoodPram saleActivityGoodPram);

    Map<String,Object> getSaleActivityById(String id);

    ResultList<PresellGoodVo> getAddGoodPageList(PresellParam param, Paging pg);

    SaleActivity getSaleActivityAndGoodByActivityId (String id);

    void saveSaleActivityGood (SaleActivity saleActivity);

    boolean IsCheckAllSkuId (SaleActivity saleActivity);
}
