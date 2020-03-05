package pub.makers.shop.store.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.base.enums.SmsType;
import pub.makers.shop.base.service.SmsService;
import pub.makers.shop.base.util.FreeMarkerHelper;
import pub.makers.shop.base.util.IdGenerator;
import pub.makers.shop.store.entity.BankCard;
import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.store.vo.BankCardVo;

import java.util.Date;
import java.util.Map;

@Service(version="1.0.0")
public class BankCardBizServiceImpl implements BankCardBizService{

	@Autowired
	private SmsService smsService;
	@Autowired
	private SubbranchService subbranchService;
	@Autowired
	private BankCardService bankcardService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void unbind(String phone, String vcode) {
		
		boolean isValidate = smsService.checkCode(SmsType.bankcard.name(), phone, vcode);
		
		ValidateUtils.isTrue(isValidate, "验证码不正确");
		BankCard bankCard = bankcardService.get(Conds.get().eq("mobile", phone));
		ValidateUtils.notNull(bankCard, "银行卡不存在");
		bankcardService.delete(Conds.get().eq("bank_card_id", bankCard.getBankCardId()));

	}

	@Override
	public void bind(BankCardExtendVo bankCardExtendVo) {
		boolean isValidate = smsService.checkCode(SmsType.bankcard.name(), bankCardExtendVo.getMobile(), bankCardExtendVo.getVerifyCode());
		ValidateUtils.isTrue(isValidate, "验证码不正确");

		BankCard check = bankcardService.get(Conds.get().eq("mobile", bankCardExtendVo.getMobile()));
		ValidateUtils.isTrue(check == null, "银行卡已存在");
		BankCard bankCard = bankCardExtendVo.toBankCard();
		bankCard.setBankCardId(IdGenerator.getDefault().nextId());
		bankCard.setCreateTime(new Date());
		bankCard.setState(0);
		bankCard.setDelet(0);
		bankCard.setConnectType(1);
		Map<String, Object> data = Maps.newHashMap();
		data.put("bankCard", bankCard);
		String sql = FreeMarkerHelper.getValueFromTpl("sql/store/insertBankCard.sql", data);
		jdbcTemplate.update(sql);
	}

	@Override
	public BankCardVo detail(String userId) {
		BankCard bankCard = bankcardService.get(Conds.get().eq("connect_id", userId));
		return new BankCardVo(bankCard);
	}

}
