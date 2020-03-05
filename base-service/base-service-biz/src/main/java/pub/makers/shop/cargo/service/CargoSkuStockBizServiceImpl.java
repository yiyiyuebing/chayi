package pub.makers.shop.cargo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lantu.base.util.ListUtils;

import pub.makers.daotemplate.vo.Conds;
import pub.makers.shop.cargo.entity.CargoSkuStock;

@Service(version="1.0.0")
public class CargoSkuStockBizServiceImpl implements CargoSkuStockBizService{

	@Autowired
	private CargoSkuStockService stockService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void updateSkuStock(List<CargoSkuStock> stockList) {
		
		List<CargoSkuStock> localStocks = stockService.list(Conds.get().in("cargoSkuId", ListUtils.getIdSet(stockList, "cargoSkuId")));
		Map<String, CargoSkuStock> localStockMap = ListUtils.toKeyMap(localStocks, "cargoSkuId");
		List<Object[]> changedStocks = Lists.newArrayList();
		
		for (CargoSkuStock u8stock : stockList){
			
			CargoSkuStock localStock = localStockMap.get(u8stock.getCargoSkuId() + "");
			if (localStock != null){
				// 当前库存-已售未发货应该等于U8库存
				// 库存不相等的，需要同步
				int locurr = localStock.getCurrStock() == null ? 0 : localStock.getCurrStock();
				int localonpay = localStock.getOnPayNo() == null ? 0 : localStock.getOnPayNo();
				int u8curr = u8stock.getCurrStock() == null ? 0 : u8stock.getCurrStock();
				if (locurr + localonpay != u8curr){
					int realStock = u8curr - localonpay;
					changedStocks.add(new Object[]{realStock, localStock.getId()});
				}
			}
		}
		
		// 批量更新库存
		if (changedStocks.size() > 0){
			jdbcTemplate.batchUpdate("update cargo_sku_stock set curr_stock = ? where id = ?", changedStocks);
		}
		
	}

	@Override
	public CargoSkuStock getStockByCargoSkuId(Long cargoSkuId) {
		return stockService.get(Conds.get().eq("cargo_sku_id", cargoSkuId));
	}


}
