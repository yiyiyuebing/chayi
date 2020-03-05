select 
		   tgs.id,
		   tgs.good_id,
		   tgs.cargo_sku_id,
		   tgs.cargo_sku_name,
		   tgs.nums,
		   tgs.sale_num,
		   tgs.market_price,
		   tgs.retail_price,
		   tgs.start_time,
		   tgs.end_time,
		   tgs.limit_num,
		   tgs.sale_price,
		   tgs.min_tuan_num,
		   tgs.on_sales_no,
		   <#if storeLevelId?? && storeLevelId != "">
		   		(select min(cssp.supply_price) from cargo_sku_supply_price cssp where cssp.cargo_sku_id = tgs.cargo_sku_id and cssp.store_level_id = ${storeLevelId}) as supply_price,
		   </#if>
		   cs.sku_item_value as skuValue,
		   cs.fixed_price,
		   cs.retail_price,
		   cst.curr_stock as cargoSkuStock
			from trade_good_sku tgs
			left join cargo_sku cs on tgs.cargo_sku_id = cs.id
			left join cargo_sku_stock cst on tgs.cargo_sku_id = cst.cargo_sku_id
		where tgs.good_id = ${goodId}
		order by tgs.sale_num desc