SELECT
	count(*)
FROM
	purchase_goods_search
WHERE
pur_goods_name LIKE "%${query.purGoodsName}%"
OR pur_subtitle LIKE "%${query.purGoodsName}%"
OR brand_name LIKE "%${query.purGoodsName}%"
OR label_name LIKE "%${query.purGoodsName}%"
<#if query.classifyIds?? && query.classifyIds != "">
  OR classify_ids IN (${query.classifyIds})
</#if>
