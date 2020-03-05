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
		   cs.sku_value
			from trade_good_sku tgs
			left join cargo_sku cs on tgs.cargo_sku_id = cs.id
		where tgs.good_id = ${goodId}
		order by tgs.sale_num desc