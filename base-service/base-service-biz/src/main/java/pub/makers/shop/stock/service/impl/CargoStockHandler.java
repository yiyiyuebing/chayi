package pub.makers.shop.stock.service.impl;

import com.dev.base.utils.SpringContextUtils;
import com.google.common.collect.Maps;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.cargo.entity.CargoSkuStock;
import pub.makers.shop.cargo.entity.CargoSkuStockLog;
import pub.makers.shop.cargo.service.CargoSkuStockLogService;
import pub.makers.shop.cargo.service.CargoSkuStockService;
import pub.makers.shop.stock.service.StockHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 货品库存操作接口
 * @author apple
 *
 */
public class CargoStockHandler implements StockHandler{
	private CargoSkuStockService cargoSkuStockService = SpringContextUtils.getBean(CargoSkuStockService.class);
	private CargoSkuStockLogService cargoSkuStockLogService = SpringContextUtils.getBean(CargoSkuStockLogService.class);

	@Override
	public void updateOnsalesNo(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		cargoSkuStockService.update(Update.byId(sku.getId()).set("curr_stock", num).set("update_time", new Date()));
	}

	@Override
	public void appendOnsalesNo(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer currStock = sku.getCurrStock() == null ? 0 : sku.getCurrStock();
		cargoSkuStockService.update(Update.byId(sku.getId()).set("curr_stock", currStock + num).set("update_time", new Date()));
	}

	@Override
	public void salesToOut(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer currStock = sku.getCurrStock() == null ? 0 : sku.getCurrStock();
//		ValidateUtils.isTrue(currStock >= num, "商品库存不足");
		sku.setCurrStock(currStock - num);
		sku.setOutShelvesNo(sku.getOutShelvesNo() == null ? num : sku.getOutShelvesNo() + num);
		cargoSkuStockService.update(Update.byId(sku.getId()).set("curr_stock", sku.getCurrStock()).set("out_shelves_no", sku.getOutShelvesNo())
				.set("update_time", new Date()));
	}

	@Override
	public void salesToPay(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer currStock = sku.getCurrStock() == null ? 0 : sku.getCurrStock();
//		ValidateUtils.isTrue(currStock >= num, "商品库存不足");
		sku.setCurrStock(currStock - num);
		sku.setOnPayNo(sku.getOnPayNo() == null ? num : sku.getOnPayNo() + num);
		cargoSkuStockService.update(Update.byId(sku.getId()).set("curr_stock", sku.getCurrStock()).set("on_pay_no", sku.getOnPayNo())
				.set("update_time", new Date()));

		createLog(sku, 4, num);
	}

	@Override
	public void payToSales(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer onPayNo = sku.getOnPayNo() == null ? 0 : sku.getOnPayNo();
		// 库存数小于修改数
		num = onPayNo < num ? onPayNo : num;
		sku.setOnPayNo(onPayNo - num);
		sku.setCurrStock(sku.getCurrStock() == null ? num : sku.getCurrStock() + num);
		cargoSkuStockService.update(Update.byId(sku.getId()).set("on_pay_no", sku.getOnPayNo()).set("curr_stock", sku.getCurrStock())
				.set("update_time", new Date()));

		createLog(sku, 4, num);
	}

	@Override
	public void payToSend(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer onPayNo = sku.getOnPayNo() == null ? 0 : sku.getOnPayNo();
		// 库存数小于修改数
		num = onPayNo < num ? onPayNo : num;
		sku.setOnPayNo(onPayNo - num);
		sku.setOnSendNo(sku.getOnSendNo() == null ? num : sku.getOnSendNo() + num);
		cargoSkuStockService.update(Update.byId(sku.getId()).set("on_pay_no", sku.getOnPayNo()).set("on_send_no", sku.getOnSendNo())
				.set("update_time", new Date()));

		createLog(sku, 5, num);
	}

	@Override
	public void sendToSales(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer onSendNo = sku.getOnSendNo() == null ? 0 : sku.getOnSendNo();
		// 库存数小于修改数
		num = onSendNo < num ? onSendNo : num;
		sku.setOnSendNo(onSendNo - num);
		sku.setCurrStock(sku.getCurrStock() == null ? num : sku.getCurrStock() + num);
		cargoSkuStockService.update(Update.byId(sku.getId()).set("on_send_no", sku.getOnSendNo()).set("curr_stock", sku.getCurrStock())
				.set("update_time", new Date()));

		createLog(sku, 5, num);
	}

	@Override
	public void sendToSeller(Long skuId, Integer num) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		ValidateUtils.isTrue(num > 0, "库存修改数必须大于零");
		Integer onSendNo = sku.getOnSendNo() == null ? 0 : sku.getOnSendNo();
		// 库存数小于修改数
		num = onSendNo < num ? onSendNo : num;
		sku.setOnSendNo(onSendNo - num);
		cargoSkuStockService.update(Update.byId(sku.getId()).set("on_send_no", sku.getOnSendNo()));

		createLog(sku, 6, num);
	}

	@Override
	public Integer getSalesNum(Long skuId) {
		CargoSkuStock sku = cargoSkuStockService.get(Conds.get().eq("cargo_sku_id", skuId));
		ValidateUtils.notNull(sku, "商品不存在");
		return sku.getCurrStock() == null ? 0: sku.getCurrStock();
	}

	@Override
	public Map<String, Integer> getSalesNum(List<Long> skuIdList) {
		List<CargoSkuStock> skuList = cargoSkuStockService.list(Conds.get().in("cargo_sku_id", skuIdList));
		Map<String, Integer> map = Maps.newHashMap();
		for (CargoSkuStock sku : skuList) {
			map.put(sku.getCargoSkuId().toString(), sku.getCurrStock() == null ? 0: sku.getCurrStock());
		}
		return map;
	}

	@Override
	public Long getCargoSkuId(Long skuId) {
		return skuId;
	}

	private CargoSkuStockLog createLog(CargoSkuStock stock, int status, int count) {
		CargoSkuStockLog log = new CargoSkuStockLog();
		log.setId(IdGenerator.getDefault().nextId());
		log.setCargoSkuId(stock.getCargoSkuId());
		log.setOutShelvesNo(stock.getOutShelvesNo());
		log.setOnSalesNo(stock.getOnSalesNo());
		log.setOnPayNo(stock.getOnPayNo());
		log.setOnSendNo(stock.getOnSendNo());
		log.setCurrStock(stock.getCurrStock());
		log.setInboundId(stock.getId());
		log.setCreateTime(new Date());
		log.setCreateBy(stock.getCreateBy());
		log.setUpdateCount(count);
		log.setUpdateStatus(status);
		cargoSkuStockLogService.insert(log);
		return log;
	}
}
