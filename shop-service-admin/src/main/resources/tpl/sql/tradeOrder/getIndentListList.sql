SELECT
	group_concat(id) as id,
	trade_good_sku_id,
	sum(number) as number,
	sum(final_amount) as final_amount,
	trade_good_name,
	trade_good_img_url,
	trade_good_amount as trade_good_amount,
	trade_good_type,
	supply_price as supply_price,
	cargo_sku_id,
	gift_flag,
	status,
	return_time,
	sum(discount_amount) as discount_amount,
	sum(return_amount) as return_amount
FROM
	indent_list
WHERE
	indent_id = ${indentId}
	and is_valid = "T"
	and del_flag = "F"
group by trade_good_sku_id