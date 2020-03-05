package pub.makers.shop.finance.service;

import pub.makers.daotemplate.service.BaseCRUDService;
import pub.makers.shop.finance.entity.FinanceAccountsPay;
import pub.makers.shop.finance.vo.FinanceAccountspayVo;

public interface FinanceAccountsPayService extends BaseCRUDService<FinanceAccountsPay>{


    /**
     * 根据订单编号查找 FinanceAccountsPay对象记录
     * @param orderId
     * @return
     */
    FinanceAccountsPay getVoByOrderId(String orderId);
}
