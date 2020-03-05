package pub.makers.shop.user.controller;

import com.dev.base.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.makers.shop.base.vo.ResultData;
import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.user.service.BankCardAppService;

@Controller
@RequestMapping("/mobile/bankcard")
public class BankCardController {

	@Autowired
	private BankCardAppService bankCardAppService;
	
	/**
	 * 解绑银行卡
	 * {/mobile/bankcard/unbind}
	 * @param phone 手机号码
	 * @param vcode 验证码
	 * @return
	 */
	@RequestMapping("unbind")
	@ResponseBody
	public ResultData unbind(String phone, String vcode){
		
		bankCardAppService.unbind(phone, vcode);
		
		return ResultData.createSuccess();
	}

	/**
	 * 绑定银行卡
	 * {/mobile/bankcard/bind}
	 * @return
	 */
	@RequestMapping("bind")
	@ResponseBody
	public ResultData bind(String modelJson){
		BankCardExtendVo bankCardExtendVo = JsonUtils.toObject(modelJson, BankCardExtendVo.class);
		bankCardAppService.bind(bankCardExtendVo);

		return ResultData.createSuccess();
	}
	
}
