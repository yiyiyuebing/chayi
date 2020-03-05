package pub.makers.shop.base.api;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.shop.base.entity.CommonText;
import pub.makers.shop.base.entity.Opinion;
import pub.makers.shop.base.service.OpinionB2bService;
import pub.makers.shop.base.vo.OpinionVo;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.baseGood.pojo.ChangeGoodNumQuery;
import pub.makers.shop.user.service.LoginB2bService;
import pub.makers.shop.user.utils.AccountUtils;

/**
 * Created by Think on 2017/8/25.
 */
@Controller
@RequestMapping("weixin/opinion")
public class OpinionApi {
    @Autowired
    private OpinionB2bService opinionB2bService;
    @Autowired
    private LoginB2bService loginB2bService;

    //意见反馈
    @RequestMapping("/advice")
    @ResponseBody
    public ResultData saveOpinion(String modelJson) {
        ValidateUtils.notNull(modelJson, "参数不能为空");
        OpinionVo opinion = JsonUtils.toObject(modelJson, OpinionVo.class);
        String userId = AccountUtils.getCurrShopId();
        opinion.setClientId(userId);
        opinionB2bService.save(opinion);
        return ResultData.createSuccess();
    }

    //关于我们
    @RequestMapping("/aboutUs")
    @ResponseBody
    public ResultData getInfoAvoutUs() {
        CommonText info = loginB2bService.getAboutUs();
        return ResultData.createSuccess(info);
    }

    //服务协议
    @RequestMapping("/serviceProtocol")
    @ResponseBody
    public ResultData getProtocol() {
        CommonText serviceProtocol = loginB2bService.getCommonText();
        return ResultData.createSuccess(serviceProtocol);
    }
}
