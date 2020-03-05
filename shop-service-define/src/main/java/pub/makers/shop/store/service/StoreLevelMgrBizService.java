package pub.makers.shop.store.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.StoreLevel;
import pub.makers.shop.store.vo.StoreLevelVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dy on 2017/6/1.
 */
public interface StoreLevelMgrBizService {
    /**
     * 获取所有店铺等级
     * @return
     */
    List<StoreLevelVo> findAllStoreLevel();

    StoreLevel findStoreLevelBySubbranchId(String subbranchId);

    Map<String,StoreLevel> getStoreLevelMap(Set<String> subbranchIdSet);

    ResultList<StoreLevelVo> storeLevelList(Paging pg);

    Map<String,Object> saveOrUpdateStoreLevel(StoreLevelVo storeLevelVo);

    ResultData updateStoreLevelStatue(String id, String statue);
}
