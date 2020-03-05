package pub.makers.shop.store.service;

import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.pojo.StoreLabelParam;
import pub.makers.shop.store.vo.StoreLabelVo;

/**
 * Created by dy on 2017/10/8.
 */
public interface StoreLabelBizService {

    /**
     * 粉丝标签列表信息
     * @param storeLabelParam
     * @return
     */
    ResultData fansLabelList(StoreLabelParam storeLabelParam);

    /**
     * 粉丝关联标签
     * @param storeLabelParam
     * @return
     */
    ResultData editFansLabel(StoreLabelParam storeLabelParam);

    /**
     * 标签列表
     * @param storeLabelParam
     * @param paging
     * @return
     */
    ResultData storeLabelList(StoreLabelParam storeLabelParam, Paging paging);

    /**
     * 保存标签及关联粉丝
     * @param storeLabelVo
     * @return
     */
    ResultData saveLabel(StoreLabelVo storeLabelVo);


    /**
     * 标签编辑信息
     * @param storeLabelVo
     * @return
     */
    ResultData getLabelInfo(StoreLabelVo storeLabelVo);

    /**
     * 查询店铺所有粉丝
     * @param storeLabelVo
     * @param paging
     * @return
     */
    ResultData getAllFansList(StoreLabelVo storeLabelVo, Paging paging);
}
