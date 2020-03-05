select COALESCE(s.supply_price,0) as supplyPrice
		FROM store_subbranch ssub 
		LEFT JOIN store_supply_price s ON s.good_sku_id = ? AND ssub.level_id = s.store_level_id
		where ssub.id=?