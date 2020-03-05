package pub.makers.shop.browseLog.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.baseOrder.enums.OrderClientType;
import pub.makers.shop.browseLog.pojo.GoodsBrowseLogQuery;
import pub.makers.shop.browseLog.service.GoodsBrowseLogB2bService;
import pub.makers.shop.browseLog.vo.GoodsBrowseLogVo;
import pub.makers.shop.user.utils.AccountUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kok on 2017/7/18.
 */
@Controller
@RequestMapping("weixin/goodsBrowseLog")
public class GoodsBrowseLogApi {
    @Autowired
    private GoodsBrowseLogB2bService goodsBrowseLogB2bService;

    /**
     * 添加足迹
     */
    @RequestMapping("/addBrowseLogList")
    @ResponseBody
    public ResultData addBrowseLogList(String skuId) {
        goodsBrowseLogB2bService.addBrowseLog(skuId);
        return ResultData.createSuccess();
    }

    /**
     * 足迹列表
     * 日期格式 yyyy-MM-dd
     */
    @RequestMapping("/getBrowseLogList")
    @ResponseBody
    public ResultData getBrowseLogList(String startDate, String endDate, String classifyId){
        ValidateUtils.notNull(startDate, "开始时间不能为空");
        ValidateUtils.notNull(endDate, "结束时间不能为空");

        String storeLevelId = AccountUtils.getCurrStoreLevelId();
        String userId = AccountUtils.getCurrShopId();
        GoodsBrowseLogQuery query = new GoodsBrowseLogQuery();
        query.setUserId(userId);
        query.setStartDate(DateParseUtil.parseDate(startDate));
        query.setEndDate(DateParseUtil.parseDate(endDate));
        query.setOrderBizType(OrderBizType.purchase);
        query.setOrderClientType(OrderClientType.weixin);
        List<GoodsBrowseLogVo> logVoList = goodsBrowseLogB2bService.getBrowseLogList(query, classifyId, storeLevelId);
        return ResultData.createSuccess("logList", logVoList);
    }

    /**
     * 删除足迹
     */
    @RequestMapping("/delBrowseLogById")
    @ResponseBody
    public ResultData delBrowseLogById(String logIds){
        ValidateUtils.notNull(logIds, "足迹id不能为空");

        String userId = AccountUtils.getCurrShopId();
        List<String> logIdList = Arrays.asList(StringUtils.split(logIds, ","));
        GoodsBrowseLogQuery query = new GoodsBrowseLogQuery();
        query.setLogIdList(logIdList);
        query.setUserId(userId);
        query.setOrderBizType(OrderBizType.purchase);
        query.setOrderClientType(OrderClientType.weixin);
        goodsBrowseLogB2bService.delBrowseLogById(query);
        return ResultData.createSuccess();
    }

    /**
     * 删除足迹
     * 日期格式 yyyy-MM-dd
     */
    @RequestMapping("/delBrowseLogByDate")
    @ResponseBody
    public ResultData delBrowseLogByDate(String startDate, String endDate){
        ValidateUtils.notNull(startDate, "开始时间不能为空");
        ValidateUtils.notNull(endDate, "结束时间不能为空");

        String userId = AccountUtils.getCurrShopId();
        GoodsBrowseLogQuery query = new GoodsBrowseLogQuery();
        query.setUserId(userId);
        query.setStartDate(DateParseUtil.parseDate(startDate));
        query.setEndDate(DateParseUtil.parseDate(endDate));
        query.setOrderBizType(OrderBizType.purchase);
        query.setOrderClientType(OrderClientType.weixin);
        goodsBrowseLogB2bService.delBrowseLogByDate(query);
        return ResultData.createSuccess();
    }
}
