package pub.makers.shop.user.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.entity.Subbranch;
import pub.makers.shop.user.pojo.WeixinFansParam;
import pub.makers.shop.user.vo.WeixinStoreWeixinuserVo;

import java.util.List;

/**
 * Created by dy on 2017/10/8.
 */
public interface WeixinFansBizService {

    /**
     * 粉丝列表
     * @param params
     * @param paging
     * @return
     */
    ResultData listByParams(WeixinFansParam params, Paging paging);


    List<String> getSubStoreIds(Subbranch subbranch);


    /**
     * 粉丝详情
     * @param params
     * @return
     */
    ResultData getFansDetail(WeixinFansParam params);


    /**
     * 获取粉丝订单相关信息
     * @param weixinFansParam
     * @return
     */
    ResultData orderRecordInfo(WeixinFansParam weixinFansParam);

    /**
     * 编辑粉丝信息
     * @param weixinStoreWeixinuserVo
     * @return
     */
    ResultData editFansInfo(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo);

    /**
     * 粉丝标签列表
     * @param weixinFansParam
     * @return
     */
    ResultData fansLabelList(WeixinFansParam weixinFansParam);

    /**
     * 编辑标签
     * @param weixinFansParam
     * @return
     */
    ResultData editLabel(WeixinFansParam weixinFansParam);

    /**
     * 获取
     * @param weixinFansParam
     * @return
     */
    ResultData getFansRemarkInfo(WeixinFansParam weixinFansParam);

    /**
     *
     * @param weixinFansParam
     * @return
     */
    ResultData getFansBelongToStore(WeixinFansParam weixinFansParam);
}
