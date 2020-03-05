package pub.makers.shop.base.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.common.util.QiNiuUtil;
import pub.makers.shop.base.vo.ResultData;

/**
 * Created by kok on 2017/8/16.
 */
@Controller
@RequestMapping("weixin/qiniu")
public class QiniuApi {

    @RequestMapping("getToken")
    @ResponseBody
    public ResultData getToken() {
        return ResultData.createSuccess("token", QiNiuUtil.getUptoken());
    }
}