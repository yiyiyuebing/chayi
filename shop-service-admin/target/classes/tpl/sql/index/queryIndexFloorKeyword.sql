SELECT
	im.id,
	im.module_name AS moduleName,
	tgc.name AS classifyName,
	im.left_ad AS leftAd,
	ad.adkey,
	rt.rtkey,
	im.date_created AS dateCreated,
	im.last_updated as lastUpdated
FROM
	index_module im
LEFT JOIN trade_goods_classify tgc ON tgc.id = im.classify_id and tgc.parent_id = '1'
LEFT JOIN (
	SELECT
		ifk.floor_id,
		group_concat(ifk.keyword) AS adkey
	FROM
		index_floor_keyword ifk
	WHERE
		ifk.post_code = 'adkey'
	GROUP BY
		ifk.floor_id
) ad ON ad.floor_id = im.id
LEFT JOIN (
	SELECT
		ifk.floor_id,
		group_concat(ifk.keyword) AS rtkey
	FROM
		index_floor_keyword ifk
	WHERE
		ifk.post_code = 'rtkey'
	GROUP BY
		ifk.floor_id
) rt ON rt.floor_id = im.id
WHERE
	im.module_type = 'floor'
AND im.del_flag = 'F'