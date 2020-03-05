SELECT
	group_concat(id) as id,
	indent_id,
	trade_good_sku_id,
	trade_good_id,
	sum(number) as number,
	sum(final_amount) as final_amount,
	trade_good_name,
	trade_good_img_url,
	trade_good_amount as trade_good_amount,
	original_amount as original_amount,
	trade_good_type,
	supply_price as supply_price,
	cargo_sku_id,
	gift_flag,
	status,
	ship_return_time,
	return_time,
	sum(discount_amount) as discount_amount,
	sum(return_amount) as return_amount,
	ship_cancel_after,
	receive_cancel_after,
	good_type
FROM
	indent_list
WHERE
	indent_id in (${indentIds})
	and is_valid = "T"
	and del_flag = "F"
	and (gift_flag = "F" or gift_flag is null)
group by trade_good_sku_id,indent_id
order by id asc