package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.store.service.BankCardBizService;
import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.user.utils.AccountUtils;

@Service
public class BankCardAppService {

	@Reference(version="1.0.0")
	private BankCardBizService bankCardBizService;
	
	public void unbind(String phone, String vcode){
		
		bankCardBizService.unbind(phone, vcode);
	}

	public void bind(BankCardExtendVo bankCardExtendVo) {
		bankCardExtendVo.setShopId(AccountUtils.getCurrShopId());
		bankCardBizService.bind(bankCardExtendVo);
	}
}
