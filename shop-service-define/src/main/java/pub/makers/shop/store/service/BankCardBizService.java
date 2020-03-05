package pub.makers.shop.store.service;

import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.store.vo.BankCardVo;

public interface BankCardBizService {

	/**
	 * 解绑银行卡
	 */
	void unbind(String phone, String vcode);

	/**
	 * 绑定银行卡
	 */
	void bind(BankCardExtendVo bankCardExtendVo);

	/**
	 * 银行卡详情
	 */
	BankCardVo detail(String userId);
}
