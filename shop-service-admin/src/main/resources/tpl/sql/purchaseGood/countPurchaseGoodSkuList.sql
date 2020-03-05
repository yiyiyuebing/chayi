<#if goodType?? && goodType=='trade' && goodType!="">
  select count(*)
	FROM
		trade_good_sku tgs
	LEFT JOIN cargo_sku cs ON cs.id = tgs.cargo_sku_id
	LEFT JOIN cargo c ON cs.cargo_id = c.id
	LEFT JOIN trade_good tg ON tg.id = tgs.good_id
	WHERE
		1 = 1
	AND tgs.`status` = '1'
	<#if relevanceName?? && relevanceName?length gt 0>
		and (tg.name like '%${relevanceName}%' or cs.code like '%${relevanceName}%' or tg.group_name = '${relevanceName}')
	</#if>
	<#if classifyId?? && classifyId?length gt 0>
		and (${classifyId})
	</#if>
	<#if startPrice?? && startPrice?length gt 0>
    and cs.retail_price >= ${startPrice}
	  </#if>
	  <#if endPrice?? && endPrice?length gt 0>
		and cs.retail_price <= ${endPrice}
	  </#if>
	  <#if isChecked?? && isChecked?length gt 0 && skuIds?? && skuIds?length gt 0>
		and tgs.id in(${skuIds})
	</#if>
	order by tg.sort desc
<#else>
  select count(*) from purchase_goods_sku pgs
  LEFT JOIN cargo_sku cs on cs.id = pgs.cargo_sku_id
  LEFT JOIN cargo c on cs.cargo_id = c.id
  LEFT JOIN purchase_goods pg on pg.id = pgs.pur_goods_id
  where 1=1
  and pgs.`status` = '1'
  <#if relevanceName?? && relevanceName?length gt 0>
    and (cs.sku_name like '%${relevanceName}%' or cs.code like '%${relevanceName}%' or pg.group_name = '${relevanceName}')
  </#if>
  <#if classifyId?? && classifyId?length gt 0>
    and (${classifyId})
  </#if>
  <#if startPrice?? && startPrice?length gt 0>
    and cs.retail_price >= ${startPrice}
  </#if>
  <#if endPrice?? && endPrice?length gt 0>
    and cs.retail_price <= ${endPrice}
  </#if>
  <#if isChecked?? && isChecked?length gt 0 && skuIds?? && skuIds?length gt 0>
    and pgs.id in(${skuIds})
  </#if>
  order by pgs.order_index desc
</#if>