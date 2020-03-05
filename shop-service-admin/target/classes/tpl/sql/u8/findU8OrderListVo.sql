select '0' as isPur, il.id as id, indent_id as order_id, trade_good_sku_id, sum(number) as number, sum(ifnull(final_amount, 0.00)) /sum(number) as final_amount, trade_good_name,
    trade_good_img_url, sum(trade_good_amount) as trade_good_amount,
    trade_good_type, sum(ifnull(supply_price, 0.00)) /sum(number) as supply_price, cs.id as cargo_sku_id, gift_flag, '0' as is_sample
		,'' as pur_packing_price, '' as pur_packing_bag_value, '' as packing_style_code, cs.code as cargo_no, c.classify_id, trade_good_id as good_id
    from indent_list il
		join cargo_sku cs  on cs.id = il.cargo_sku_id
		join cargo c on c.id = cs.cargo_id
    where indent_id = ${orderId} GROUP BY trade_good_sku_id
    UNION ALL
    select '1' as isPur,
    pol.id as id, order_id, pur_goods_sku_id as trade_good_sku_id,  sum(number) as number, sum(ifnull(final_amount, 0.00)) /sum(number) as final_amount, pur_goods_name as trade_good_name, pur_goods_img_url as trade_good_img_url,
    sum(pur_goods_amount) as trade_good_amount, pur_goods_type as trade_good_type, sum(ifnull(supply_price, 0.00)) /sum(number) as supply_price, cs.id as cargo_sku_id, gift_flag,
		is_sample, pur_packing_price, pur_packing_bag_value, packing_style_code as packing_style_code
		,cs.code as cargo_no, c.classify_id, pur_goods_id as good_id
    FROM purchase_order_list pol
		join cargo_sku cs  on cs.id = pol.cargo_sku_id
		join cargo c on c.id = cs.cargo_id
    where order_id = ${orderId} GROUP BY pol.pur_goods_sku_id