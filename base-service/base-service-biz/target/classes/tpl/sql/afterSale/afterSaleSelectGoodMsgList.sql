SELECT
	group_concat(pol.id) as id,
	sum(pol.final_amount)/ sum(pol.number) as final_amount,
	pol.pur_goods_name,
	po.status,
	pol.pur_goods_img_url,
	po.order_no,
	po.order_type,
	po.id as orderId,
	sum(pol.number) as number,
	cs.sku_name,
	pol.return_time,
	pol.ship_return_time
FROM
	purchase_order_list pol
	left join purchase_order po on po.id = pol.order_id
	left join cargo_sku cs on cs.id = pol.cargo_sku_id
WHERE 1=1
and pol.order_id = '${orderId}'
AND pol.pur_goods_sku_id = '${skuId}'
and po.buyer_id = '${shopId}'
group BY pur_goods_sku_id;
