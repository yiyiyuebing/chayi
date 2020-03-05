SELECT
	a.*
FROM
	(
		SELECT
			*
		FROM
			purchase_goods_sku
		ORDER BY
			sale_num DESC
	) a
WHERE 1=1
	and del_flag != 'T'
	and a.pur_goods_id IN (${goodIds})
GROUP BY
	a.pur_goods_id;