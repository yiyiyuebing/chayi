<#if orderBizType ?? && orderBizType =="purchase">
  SELECT
    pgs.id,
		cs.code as skuId,
    pgs.pur_goods_id as goodId,
    pg.pur_goods_name as name,
    (select i.pic_url from image i where i.group_id = c.pc_album_id limit 1) as image,
    TRUNCATE(csp.referencePrice,2) as retail_price,
    TRUNCATE((cs.retail_price - 10 - csp.referencePrice) * 0.93,2) as referencePrice,
			CASE
		WHEN spgs.id IS NOT NULL THEN
			'TT'
		END AS isJoin
  FROM
    purchase_goods_sku pgs
  LEFT JOIN purchase_goods pg ON pgs.pur_goods_id = pg.id
  LEFT JOIN cargo_sku cs ON cs.id = pgs.cargo_sku_id
  LEFT JOIN cargo c ON c.id = cs.cargo_id
	LEFT JOIN (
			SELECT spg.*
			FROM
				sp_presell_good spg,
				sp_presell_activity spa
			WHERE
				spa.id = spg.activity_id
			AND spa.del_flag = 'F'
			AND spa.presell_end > SYSDATE()
		) spgs ON spgs.sku_id = pgs.id
		AND spgs.del_flag = 'F'
  LEFT JOIN (
    SELECT
      cssp.cargo_sku_id,
      max(cssp.supply_price) AS referencePrice
    FROM
      cargo_sku_supply_price cssp
    LEFT JOIN cargo_sku ON cargo_sku.id = cssp.cargo_sku_id
    WHERE
      cssp.cargo_sku_id = cargo_sku.id
    AND cssp.store_level_id in (select sl.level_Id from store_level sl where sl.name in ('加盟商','联盟商','特殊加盟'))
    GROUP BY
      cssp.cargo_sku_id
  ) csp ON csp.cargo_sku_id = cs.id
  where 1=1 and pgs.status ='1'
  <#if classifyId?? && classifyId!="">
    and (${classifyId})
  </#if>
  <#if cargoNo?? && cargoNo!="">
   and c.cargo_no = '${cargoNo}'
  </#if>
  <#if presellName?? && presellName !="">
   and (pg.pur_goods_name like '%${presellName}%' or cs.code = '${presellName}' )
  </#if>
  <#if isCheck?? && isCheck!="">
    and pgs.id in (${skuIds})
  </#if>
	order by pg.order_index desc
<#else>
 SELECT
    tgs.id,
    cs.code as skuId,
    tgs.good_id as goodId,
    tg.`name`,
    (select i.pic_url from image i where i.group_id = c.pc_album_id limit 1) as image,
    TRUNCATE(cs.retail_price,2) as retail_price,
    TRUNCATE((cs.retail_price - 10 - csp.referencePrice)* 0.93,2) as referencePrice,
			CASE
		WHEN spgs.id IS NOT NULL THEN
			'TT'
		END AS isJoin
  FROM
    trade_good_sku tgs
  LEFT JOIN trade_good tg ON tg.id = tgs.good_id
  LEFT JOIN cargo_sku cs ON cs.id = tgs.cargo_sku_id
  LEFT JOIN cargo c ON c.id = cs.cargo_id
	LEFT JOIN (
		SELECT spg.*
		FROM
			sp_presell_good spg,
			sp_presell_activity spa
		WHERE
			spa.id = spg.activity_id
		AND spa.del_flag = 'F'
		AND spa.presell_end > SYSDATE()
	) spgs ON spgs.sku_id = tgs.id
	AND spgs.del_flag = 'F'
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
  where 1=1 and tgs.status = '1'
  <#if classifyId?? && classifyId!="">
    and (${classifyId})
  </#if>
  <#if cargoNo?? && cargoNo!="">
    and c.`cargo_no` = '${cargoNo}'
  </#if>
  <#if presellName?? && presellName !="">
    and (tg.name like '%${presellName}%' or cs.code = '${presellName}')
  </#if>
  <#if isCheck?? && isCheck!="">
    and tgs.id in (${skuIds})
  </#if>
	order by tg.sort desc
</#if>
