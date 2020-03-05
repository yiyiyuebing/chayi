package pub.makers.shop.store.service;

import java.util.List;
import java.util.Map;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.store.vo.SubbranchVo;

public interface SubbranchAccountMgrBizService {

    /**
     * 子账号列表
     *
     * @param subbranchVo
     * @param paging
     * @return
     */
    ResultList<SubbranchVo> listSubbranchAccountByParent(SubbranchVo subbranchVo, Paging paging);

    /**
     * 检查手机号（帐号）唯一性
     *
     * @param mobile
     * @param id
     * @return
     */
    Map<String, Object> checkSubAccountMobile(String mobile, Long id);

}
