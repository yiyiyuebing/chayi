package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.base.vo.Paging;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.user.pojo.WeixinFansParam;
import pub.makers.shop.user.service.WeixinFansBizService;
import pub.makers.shop.user.vo.WeixinStoreWeixinuserVo;

/**
 * Created by dy on 2017/10/8.
 */
@Service
public class WeixinFansAppService {


    @Reference(version = "1.0.0")
    private WeixinFansBizService weixinFansBizService;

    public ResultData getFansList(WeixinFansParam weixinFansParam, Paging paging) {
        return weixinFansBizService.listByParams(weixinFansParam, paging);
    }

    public ResultData getFansDetail(WeixinFansParam weixinFansParam) {
        return weixinFansBizService.getFansDetail(weixinFansParam);
    }

    public ResultData orderRecordInfo(WeixinFansParam weixinFansParam) {
        return weixinFansBizService.orderRecordInfo(weixinFansParam);
    }

    public ResultData editFansInfo(WeixinStoreWeixinuserVo weixinStoreWeixinuserVo) {
        return weixinFansBizService.editFansInfo(weixinStoreWeixinuserVo);
    }

    public ResultData fansLabelList(WeixinFansParam weixinFansParam) {
        return weixinFansBizService.fansLabelList(weixinFansParam);
    }

    public ResultData editLabel(WeixinFansParam weixinFansParam) {
        return weixinFansBizService.editLabel(weixinFansParam);
    }

    public ResultData getFansRemarkInfo(WeixinFansParam weixinFansParam) {
        return weixinFansBizService.getFansRemarkInfo(weixinFansParam);
    }

    public ResultData getFansBelongToStore(WeixinFansParam weixinFansParam) {
        return weixinFansBizService.getFansBelongToStore(weixinFansParam);
    }
}
