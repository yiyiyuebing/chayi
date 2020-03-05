package pub.makers.shop.base.service;

import pub.makers.shop.base.entity.SysDict;
import pub.makers.shop.base.vo.MsgTemplateParams;
import pub.makers.shop.base.vo.MsgTemplateVo;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultList;

import java.util.Map;

/**
 * Created by dy on 2017/5/15.
 */
public interface MsgTemplateMgrBizService {

    /**
     * 通过参数查询相应的信息模板列表
     * @param param
     * @param pg
     * @return
     */
    ResultList<MsgTemplateVo> listByCondition(MsgTemplateParams param, Paging pg);

    /**
     * 将时间的值设为 1
     * @return
     */
    SysDict editTime();

    /**
     * 保存更改后的时间的值
     * @param param
     */
    void saveTime(SysDict param);

    /**
     *根据参数更改禁用状态(0改为1,1改为0)
     * @param param
     * @param pg
     * @return
     */
    Map<String, Object> changeState(MsgTemplateParams param, Paging pg);

    /**
     * 保存更改后的订单短信
     * @param param
     */
    void saveOrderMsg(MsgTemplateParams param);
}
