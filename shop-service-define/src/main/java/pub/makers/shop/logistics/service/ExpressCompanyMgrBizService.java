package pub.makers.shop.logistics.service;

import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;

import java.util.Map;

/**
 * Created by dy on 2017/4/26.
 */
public interface ExpressCompanyMgrBizService {

    /**
     * 快递公司列表
     * @param sysDict
     * @param pg
     * @return
     */
    ResultList<SysDict> expressCompanyList(SysDict sysDict, Paging pg);

    /**
     * 保存或更新快递公司
     * @param sysDict
     * @return
     */
    SysDict saveOrUpdate(SysDict sysDict);

    /**
     * 检查快递公司编码的唯一性
     * @param sysDict
     * @return
     */
    Map<String,Object> checkUictType(SysDict sysDict);

    /**
     * 删除快递公司
     * @param id
     * @return
     */
    SysDict delExpressCompanyById(String id);
}
