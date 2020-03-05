package pub.makers.shop.tradeGoods.service;


import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.tradeGoods.pojo.GoodsColumnPram;
import pub.makers.shop.tradeGoods.vo.GoodsColumnVo;


/**
 * Created by devpc on 2017/8/8.
 */
public interface GoodsColumnMgrBizService {

        ResultList<GoodsColumnVo> selectGoodsColumnByColUmnName(GoodsColumnPram goodsColumnPram, Paging paging);  //根据名字查询

        boolean editGoodsColumn(GoodsColumnVo goodsColumnVo, String userId);
}
