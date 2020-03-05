SELECT
	smg.*, (
		SELECT
			i.pic_url
		FROM
			image i
		WHERE
			i.group_id = c.pc_album_id
		LIMIT 1
	) AS goodImgUrl
FROM
	sp_manzeng_good smg
<#if orderBizType ?? && orderBizType =="purchase">
LEFT JOIN purchase_goods_sku tgs ON tgs.id = smg.good_sku_id
<#else>
LEFT JOIN trade_good_sku tgs ON tgs.id = smg.good_sku_id
</#if>
LEFT JOIN cargo_sku cs ON cs.id = tgs.cargo_sku_id
LEFT JOIN cargo c ON c.id = cs.cargo_id
WHERE 1=1
AND smg.activity_id in (${activityIds})
AND smg.del_flag != 'T'
GROUP BY
	smg.activity_id, smg.good_sku_id;