SELECT
	gps.num AS onSalesNo,
  s.curr_stock AS outShelvesNo,
	s.on_pay_no AS onPayNo,
	t.cargo_sku_id AS cargoSkuId,
	t.id,
	c.name AS skuName,
	t.pur_goods_id AS goodId,
	gps.is_valid as isValid
FROM
	purchase_goods_sku t
Left JOIN good_putaway_schedule gps on gps.good_type = 'purchase' and gps.sku_id = t.id and gps.del_flag = 'F'
LEFT JOIN cargo_sku c ON c.id = t.cargo_sku_id
LEFT JOIN cargo_sku_stock s ON s.cargo_sku_id = t.cargo_sku_id
WHERE
	1 = 1 and t.id = ?