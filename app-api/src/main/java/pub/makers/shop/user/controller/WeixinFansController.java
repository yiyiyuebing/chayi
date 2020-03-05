package pub.makers.shop.user.controller;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.pojo.StoreLabelParam;
import pub.makers.shop.user.pojo.WeixinFansParam;
import pub.makers.shop.user.service.WeixinFansAppService;
import pub.makers.shop.user.vo.WeixinStoreWeixinuserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dy on 2017/10/8.
 */
@Controller
@RequestMapping("user/fans")
public class WeixinFansController {

    @Autowired
    private WeixinFansAppService weixinFansAppService;

    @RequestMapping("getFansList")
    @ResponseBody
    public ResultData getFansList(HttpServletRequest request, WeixinFansParam weixinFansParam) {
        ValidateUtils.notNull(weixinFansParam, "查询参数为空");
        Paging paging = Paging.build2(request);
        return weixinFansAppService.getFansList(weixinFansParam, paging);
    }

    @RequestMapping("getFansDetail")
    @ResponseBody
    public ResultData getFansDetail(HttpServletRequest request, WeixinFansParam weixinFansParam) {
        ValidateUtils.notNull(weixinFansParam, "参数为空");
        ValidateUtils.notNull(weixinFansParam.getShopId(), "参数为空");
        ValidateUtils.notNull(weixinFansParam.getFansId(), "参数为空");
        return weixinFansAppService.getFansDetail(weixinFansParam);
    }

    @RequestMapping("editFansInfo")
    @ResponseBody
    public ResultData editFansInfo(HttpServletRequest request, WeixinStoreWeixinuserVo weixinStoreWeixinuserVo) {
        ValidateUtils.notNull(weixinStoreWeixinuserVo, "参数为空");
        return weixinFansAppService.editFansInfo(weixinStoreWeixinuserVo);
    }

    @RequestMapping("orderRecordInfo")
    @ResponseBody
    public ResultData orderRecordInfo(HttpServletRequest request, WeixinFansParam weixinFansParam) {
        ValidateUtils.notNull(weixinFansParam, "参数为空");
        ValidateUtils.notNull(weixinFansParam.getShopId(), "参数为空");
        ValidateUtils.notNull(weixinFansParam.getFansId(), "参数为空");
        return weixinFansAppService.orderRecordInfo(weixinFansParam);
    }

    @RequestMapping("getFansRemarkInfo")
    @ResponseBody
    public ResultData getFansRemarkInfo(HttpServletRequest request, WeixinFansParam weixinFansParam) {
        ValidateUtils.notNull(weixinFansParam, "参数为空");
        ValidateUtils.notNull(weixinFansParam.getShopId(), "参数为空");
        ValidateUtils.notNull(weixinFansParam.getFansId(), "参数为空");
        return weixinFansAppService.getFansRemarkInfo(weixinFansParam);
    }

    @RequestMapping("getFansBelongToStore")
    @ResponseBody
    public ResultData getFansBelongToStore(HttpServletRequest request, WeixinFansParam weixinFansParam) {
        ValidateUtils.notNull(weixinFansParam, "参数为空");
        ValidateUtils.notNull(weixinFansParam.getShopId(), "参数为空");
        ValidateUtils.notNull(weixinFansParam.getFansId(), "参数为空");
        return weixinFansAppService.getFansBelongToStore(weixinFansParam);
    }


}
