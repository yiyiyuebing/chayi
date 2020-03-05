package pub.makers.shop.user.api;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.store.vo.BankCardVo;
import pub.makers.shop.user.service.BankCardB2bService;

/**
 * Created by kok on 2017/8/7.
 */
@Controller
@RequestMapping("weixin/bankCard")
public class BankCardApi {

    @Autowired
    private BankCardB2bService bankCardB2bService;

    /**
     * 解绑银行卡
     * @param phone 手机号码
     * @param vcode 验证码
     * @return
     */
    @RequestMapping("unbind")
    @ResponseBody
    public ResultData unbind(String phone, String vcode){

        bankCardB2bService.unbind(phone, vcode);

        return ResultData.createSuccess();
    }

    /**
     * 绑定银行卡
     * @return
     */
    @RequestMapping("bind")
    @ResponseBody
    public ResultData bind(String modelJson){
        BankCardExtendVo bankCardExtendVo = JsonUtils.toObject(modelJson, BankCardExtendVo.class);
        bankCardB2bService.bind(bankCardExtendVo);

        return ResultData.createSuccess();
    }

    /**
     * 银行卡详情
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public ResultData detail(){
       BankCardVo vo =  bankCardB2bService.detail();

        return ResultData.createSuccess(vo);
    }
}
