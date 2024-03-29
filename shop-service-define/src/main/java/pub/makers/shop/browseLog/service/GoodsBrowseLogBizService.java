package pub.makers.shop.browseLog.service;

import pub.makers.shop.browseLog.pojo.GoodsBrowseLogQuery;
import pub.makers.shop.browseLog.vo.GoodsBrowseLogVo;

import java.util.List;

/**
 * Created by kok on 2017/6/23.
 */
public interface GoodsBrowseLogBizService {
    /**
     * 添加足迹
     */
    void addBrowseLog(GoodsBrowseLogVo logVo);

    /**
     * 足迹列表
     */
    List<GoodsBrowseLogVo> getBrowseLogList(GoodsBrowseLogQuery query);

    /**
     * 删除足迹
     */
    void delBrowseLogById(GoodsBrowseLogQuery query);

    /**
     * 删除足迹
     */
    void delBrowseLogByDate(GoodsBrowseLogQuery query);
}
