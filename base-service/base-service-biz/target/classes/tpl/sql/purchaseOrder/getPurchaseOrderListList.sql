SELECT
	group_concat(id) as id,
	order_id,
	pur_goods_sku_id,
	pur_goods_id,
	status,
	sum(number) as number,
	sum(final_amount) as final_amount,
	pur_goods_name,
	pur_goods_img_url,
	pur_goods_amount as pur_goods_amount,
	pur_goods_type,
	ROUND(supply_price / number, 2) as supply_price,
	create_time,
	is_sample,
	sum(sum_amount) as sum_amount,
	cargo_sku_id,
	sum(original_amount) as original_amount,
	sum(discount_amount) as discount_amount,
	ship_return_time,
	return_time,
	last_updated,
	gift_flag,
	sum(return_amount) as return_amount,
	ship_cancel_after,
	receive_cancel_after
FROM
	purchase_order_list
WHERE
	order_id in (${orderIds})
	and is_valid = 'T'
	and del_flag = 'F'
	and (gift_flag = 'F' or gift_flag is null)
group by pur_goods_sku_id,order_id