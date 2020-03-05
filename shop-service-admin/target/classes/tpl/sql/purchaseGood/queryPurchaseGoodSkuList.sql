<#if goodType?? && goodType=='trade' && goodType!="">
	SELECT
		tgs.id,
		tgs.good_id AS goodsId,
		tg.`name` AS NAME,
		cs.retail_price,
		(select i.pic_url from image i where i.group_id = cs.cover_img limit 1) as smallImage
	FROM
		trade_good_sku tgs
	LEFT JOIN cargo_sku cs ON cs.id = tgs.cargo_sku_id
	LEFT JOIN cargo c ON cs.cargo_id = c.id
	LEFT JOIN trade_good tg ON tg.id = tgs.good_id
	WHERE
		1 = 1
	AND tgs.`status` = '1'
	<#if relevanceName?? && relevanceName!="">
		and (tg.name like '%${relevanceName}%' or cs.code = '${relevanceName}' or cs.name = '%${relevanceName}%')
	</#if>
	<#if classifyId?? && classifyId!="">
		and (${classifyId})
	</#if>
	<#if startPrice?? && startPrice!=0>
		and cs.retail_price >= ${startPrice}
	</#if>
	<#if endPrice?? && endPrice!=0>
		and cs.retail_price <= ${endPrice}
	</#if>
	<#if isChecked?? && isChecked!="">
		and tgs.id in(${skuIds})
	</#if>
	order by tg.sort desc LIMIT ?,?
<#else>
	select pgs.id,pgs.pur_goods_id as goodsId,pg.pur_goods_name as name,cs.retail_price,
	(select i.pic_url from image i where i.group_id = cs.cover_img limit 1) as smallImage
	from purchase_goods_sku pgs
	LEFT JOIN cargo_sku cs on cs.id = pgs.cargo_sku_id
	LEFT JOIN cargo c on cs.cargo_id = c.id
	LEFT JOIN purchase_goods pg on pg.id = pgs.pur_goods_id
	where 1=1
	and pgs.`status` = '1'
	<#if relevanceName?? && relevanceName!="">
		and (pg.pur_goods_name like '%${relevanceName}%' or cs.code = '${relevanceName}' or cs.name like '%${relevanceName}%')
	</#if>
	<#if classifyId?? && classifyId!="">
		and (${classifyId})
	</#if>
	<#if startPrice?? && startPrice!=0>
		and cs.retail_price >= ${startPrice}
	</#if>
	<#if endPrice?? && endPrice!=0>
		and cs.retail_price <= ${endPrice}
	</#if>
	<#if isChecked?? && isChecked!="">
		and pgs.id in(${skuIds})
	</#if>
	order by pgs.order_index desc LIMIT ?,?
</#if>


