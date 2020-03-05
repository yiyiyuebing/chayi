SELECT
	css.id,
	cs.`code` AS skuCode,
	cs. NAME AS cargoSkuName,
	cs. sku_name AS cargoSKuValue,
	c.cargo_no AS goodsCode,
	c.`name` AS goodsName,
	cc. NAME AS classify,
	cb. NAME AS brand,
	sum(
		IFNULL(pgs.on_sales_no, 0) + IFNULL(tgs.on_sales_no, 0)
	) AS onSalesNo,
	sum(
		(
			IFNULL(pgs.on_pay_no, 0) + IFNULL(tgs.on_pay_no, 0)
		)
	) AS onPayNo,
	IFNULL(
			(
				IFNULL(pgs.on_send_no, 0) + IFNULL(tgs.on_send_no, 0)
			),
			0
		) AS onSendNo,
	IFNULL(
				tgs.on_sales_no, 0
			) AS tradeSaleNo,
	IFNULL(
					pgs.on_sales_no, 0
				) AS purchaseSaleNo
FROM
	cargo_sku_stock css
LEFT JOIN cargo_sku cs ON cs.id = css.cargo_sku_id
LEFT JOIN cargo c ON c.id = cs.cargo_id
LEFT JOIN cargo_classify cc ON cc.id = c.classify_id
LEFT JOIN cargo_brand cb ON cb.id = c.brand_id
LEFT JOIN purchase_goods_sku pgs ON pgs.cargo_sku_id = cs.id
LEFT JOIN trade_good_sku tgs ON tgs.cargo_sku_id = cs.id
WHERE
	1 = 1
AND css.id = ${stockId}  group by css.id;