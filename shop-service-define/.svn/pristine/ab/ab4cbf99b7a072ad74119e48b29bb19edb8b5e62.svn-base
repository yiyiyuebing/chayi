package pub.makers.shop.store.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.SalesReturnReason;
import pub.makers.shop.store.vo.SalesReturnReasonVo;

import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2017/6/24.
 */
public interface SalesReturnReasonBizService {

    /**
     * 获取信息
     * @param id
     * @return
     */
    SalesReturnReason getById(String id);

    /**
     * 售后理由列表
     */
    List<SalesReturnReasonVo> getReasonList();

    /**
     *售前售后原因管理
     */
    ResultList<SalesReturnReasonVo> getSalesReturnReason(Paging pg) ;

    /**
     * 删除
     * @param ids
     * @return
     */
    Map<String, Object> deletSalesReturnReason(String ids);

    /**
     * 增加原因
     * @param salesReturnReason
     * @return
     */
    Map<String, Object> saveOrUpdateSalesReturnReason(SalesReturnReason salesReturnReason);
}
