SELECT
	group_concat(id),
	order_id,
	pur_goods_id,
	status,
	sum(number) as number,
	sum(final_amount) as final_amount,
	pur_goods_name,
	pur_goods_img_url,
	pur_goods_amount as pur_goods_amount,
	pur_goods_type,
	supply_price as supply_price,
	create_time,
	is_sample,
	sum(sum_amount) as sum_amount,
	cargo_sku_id,
	sum(original_amount) as original_amount,
	sum(discount_amount) as discount_amount,
	return_time,
	last_updated,
	gift_flag,
	sum(return_amount) as return_amount
FROM
	purchase_order_list
WHERE
	order_id = ${orderId}
	and is_valid = "T"
	and del_flag = "F"
group by pur_goods_sku_id