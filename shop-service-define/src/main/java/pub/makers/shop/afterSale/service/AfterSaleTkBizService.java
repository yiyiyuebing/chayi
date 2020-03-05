package pub.makers.shop.afterSale.service;

import pub.makers.shop.afterSale.vo.AfterSaleApply;
import pub.makers.shop.afterSale.vo.AfterSaleTkResult;

/**
 * 订单退款业务服务
 * @author apple
 *
 */
public interface AfterSaleTkBizService {

	/**
	 * 查询订单退款结果
	 * @param apply
	 * @return
	 */
	AfterSaleTkResult queryTkResult(AfterSaleApply apply);
}
