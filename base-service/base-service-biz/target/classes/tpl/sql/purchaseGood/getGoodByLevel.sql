SELECT
	pg.*
FROM
	(SELECT * FROM purchase_goods_search WHERE find_in_set("${storeLevelId}", store_level)) pgs
	JOIN purchase_goods pg ON pg.id = pgs.pur_goods_id
WHERE
	pg.id IN (${goodIds})
<#if status?? && status != "">
  AND pg.STATUS = "${status}"
</#if>
AND pg.classify_valid = "T"
ORDER BY
	order_index DESC