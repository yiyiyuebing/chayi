<#if orderBizType ?? && orderBizType =="purchase">
  SELECT
    pgs.id,
		cs.code as skuId,
    pgs.pur_goods_id as goodId,
    pg.pur_goods_name as name,
    c.pc_album_id AS groupId,
    spgs.activity_id AS activityId,
    TRUNCATE(csp.referencePrice,2) as retail_price,
    csp.retail_price AS purchaseOnePrice,
    TRUNCATE((cs.retail_price - 10 - csp.referencePrice) * 0.93,2) as referencePrice
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
      cargo_sku.retail_price,
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
  where 1=1 and pgs.status ='1' and
  not exists (select * from sp_manzeng_good smg
		left join sp_manzeng_activity sma on sma.id = smg.activity_id where smg.good_sku_id = pgs.id AND smg.del_flag='F' and sma.end_time > SYSDATE() and sma.del_flag ='F' and sma.order_biz_type = '${orderBizType}')
  <#if classifyId?? && classifyId!="">
    and ${classifyId}
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
     c.pc_album_id AS groupId,
    TRUNCATE(cs.retail_price,2) as retail_price,
    spgs.activity_id,
    TRUNCATE((cs.retail_price - 10 - csp.referencePrice)* 0.93,2) as referencePrice
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
  where 1=1 and tgs.status = '1' and
  not exists (select * from sp_manzeng_good smg
		left join sp_manzeng_activity sma on sma.id = smg.activity_id where smg.good_sku_id = tgs.id  AND smg.del_flag='F' and sma.end_time > SYSDATE() and sma.order_biz_type = '${orderBizType}')
  <#if classifyId?? && classifyId!="">
    and ${classifyId}
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
