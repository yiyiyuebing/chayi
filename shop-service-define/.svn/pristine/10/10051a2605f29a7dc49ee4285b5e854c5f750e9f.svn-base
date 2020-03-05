package pub.makers.shop.cargo.service;

import java.util.List;

import pub.makers.shop.cargo.entity.CargoSkuStock;

/**
 * 订单库存操作接口
 * @author apple
 *
 */
public interface CargoSkuStockBizService {

	/**
	 * 批量更新货品SKU的库存
	 * @param stockList
	 */
	void updateSkuStock(List<CargoSkuStock> stockList);

	/**
	 * 通过SkuId获取库存信息
	 * @param cargoSkuId
	 * @return
	 */
	CargoSkuStock getStockByCargoSkuId(Long cargoSkuId);
}
