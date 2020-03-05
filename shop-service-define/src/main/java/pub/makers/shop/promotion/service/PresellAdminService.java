package pub.makers.shop.promotion.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.promotion.entity.PresellActivity;
import pub.makers.shop.promotion.entity.PresellGood;
import pub.makers.shop.promotion.vo.PresellActivityVo;
import pub.makers.shop.promotion.vo.PresellGoodVo;
import pub.makers.shop.promotion.vo.PresellParam;

import java.util.List;

/**
 * Created by daiwenfa on 2017/6/19.
 */
public interface PresellAdminService {

    ResultList<PresellActivityVo> getPageList(PresellParam param, Paging pg);

    boolean remove(String id);

    boolean ableOrDisable(String id, String operation, long userId);

    void addOrUpdate(PresellActivity presellActivity, long userId);

    PresellActivity getPresellActivityData(String id);

    ResultList<PresellGoodVo> getAddGoodPageList(PresellParam param, Paging pg);

    void savePresellGood(PresellActivity presellActivity);

    boolean IsCheckAllSkuId(PresellActivity presellActivity);

    List<PresellGood> getPresellActivityDataList(String idStr);
}
