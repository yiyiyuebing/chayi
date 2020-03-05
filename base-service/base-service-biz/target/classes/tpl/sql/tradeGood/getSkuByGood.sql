SELECT
	a.id
FROM
	(
		SELECT
			*
		FROM
			trade_good_sku
		ORDER BY
			sale_num DESC
	) a
WHERE 1=1
	and del_flag != 'T'
	and a.good_id IN (${goodIds})
GROUP BY
	a.good_id