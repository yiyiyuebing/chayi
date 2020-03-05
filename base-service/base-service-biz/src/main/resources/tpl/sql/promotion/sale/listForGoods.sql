SELECT
	a.id,
	a.good_sku_id,
	'sale' AS activity_type,
	'打折' AS activity_name,
	b. NAME AS activity_full_name,
	b.sale_desc AS activity_desc,
	0 AS discount_amount,
	b.tag1,
	b.tag2,
	b.tag1_valid,
	b.tag2_valid,
	b.start_time AS activity_start,
	b.end_time AS activity_end,
	a.sale_type,
	b.limit_flag as limit_flg,
	b.limit_num,
	b.limit_unit,
	a.vm_num,
	a.buy_num,
	a.max_num,
	b.memo,
	a.activity_price AS final_amount
FROM
	sp_sale_activity_good a,
	sp_sale_activity b
WHERE
	a.activity_id = b.id
AND a.is_valid = 'T'
AND a.del_flag = 'F'
AND b.is_valid = 'T'
AND b.del_flag = 'F'
and b.end_time >= now()
AND a.good_sku_id IN (%s)