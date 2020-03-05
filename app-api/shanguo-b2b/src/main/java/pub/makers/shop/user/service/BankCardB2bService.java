package pub.makers.shop.user.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import pub.makers.shop.store.service.BankCardBizService;
import pub.makers.shop.store.vo.BankCardExtendVo;
import pub.makers.shop.store.vo.BankCardVo;
import pub.makers.shop.user.utils.AccountUtils;

/**
 * Created by kok on 2017/8/7.
 */
@Service
public class BankCardB2bService {
    @Reference(version="1.0.0")
    private BankCardBizService bankCardBizService;

    public void unbind(String phone, String vcode){

        bankCardBizService.unbind(phone, vcode);
    }

    public void bind(BankCardExtendVo bankCardExtendVo) {
        bankCardExtendVo.setShopId(AccountUtils.getCurrShopId());
        bankCardBizService.bind(bankCardExtendVo);
    }

    public BankCardVo detail() {
        String userId = AccountUtils.getCurrShopId();
        return bankCardBizService.detail(userId);
    }
}
