<#if goodType?? && goodType=='trade' && goodType!="">
	SELECT
		tgs.id,
		tgs.good_id AS goodsId,
		tg.`name` AS NAME,
		cs.code as skuCode,
		cs.retail_price,
		(IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0)) AS currStock,
		cs.cover_img as smallImage
	FROM
		trade_good_sku tgs
	LEFT JOIN cargo_sku cs ON cs.id = tgs.cargo_sku_id
	LEFT JOIN cargo_sku_stock css ON cs.id = css.cargo_sku_id
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
	select
	pgs.id,
	pgs.pur_goods_id as goodsId,
	pg.pur_goods_name as name,
	cs.code as skuCode,
	csp.referencePrice as retail_price,
	(IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0)) AS currStock,
	cs.cover_img as smallImage
	from purchase_goods_sku pgs
	LEFT JOIN cargo_sku cs on cs.id = pgs.cargo_sku_id
	LEFT JOIN cargo_sku_stock css ON cs.id = css.cargo_sku_id
	LEFT JOIN cargo c on cs.cargo_id = c.id
	LEFT JOIN good_putaway_schedule ON pgs.id = good_putaway_schedule.sku_id
	AND good_putaway_schedule.del_flag = 'F'
	AND good_putaway_schedule.is_valid = 'T'
	AND good_putaway_schedule.good_type = 'purchase'
	AND good_putaway_schedule.is_success IS NULL
	LEFT JOIN (
    SELECT
      cssp.cargo_sku_id,
      max(cssp.supply_price) as referencePrice
    FROM
      cargo_sku_supply_price cssp
  LEFT JOIN cargo_sku on cargo_sku.id = cssp.cargo_sku_id
    WHERE
      cssp.cargo_sku_id = cargo_sku.id
    AND cssp.store_level_id in (select sl.level_Id from store_level sl where sl.name in ('加盟商','联盟商','特殊加盟'))
  group by cssp.cargo_sku_id
  ) csp on csp.cargo_sku_id = cs.id
	LEFT JOIN purchase_goods pg on pg.id = pgs.pur_goods_id
	where 1=1
	and pgs.`status` = '1'
	<#if relevanceName?? && relevanceName?length gt 0>
		and (pg.pur_goods_name like '%${relevanceName}%' or cs.code = '${relevanceName}' or cs.name like '%${relevanceName}%')
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
	order by pgs.order_index desc LIMIT ?,?
</#if>


