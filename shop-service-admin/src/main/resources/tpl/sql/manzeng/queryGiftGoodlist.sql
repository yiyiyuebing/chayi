<#if orderBizType ?? && orderBizType =="purchase">
  SELECT
    pgs.id,
	cs.code as skuId,
    pgs.pur_goods_id as goodId,
    pg.pur_goods_name as name,
    cs.cover_img as image,
    (IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0)) AS u8_stock,
    TRUNCATE(csp.referencePrice,2) as retail_price,
    TRUNCATE((cs.retail_price - 10 - csp.referencePrice) * 0.93, 2) as referencePrice
  FROM
    purchase_goods_sku pgs
  LEFT JOIN purchase_goods pg ON pgs.pur_goods_id = pg.id
  LEFT JOIN cargo_sku cs ON cs.id = pgs.cargo_sku_id
  LEFT JOIN cargo_sku_stock css ON css.cargo_sku_id = pgs.cargo_sku_id
  LEFT JOIN cargo c ON c.id = cs.cargo_id
	LEFT JOIN (
			SELECT smg.*
			FROM
			    sp_manzeng_good smg,
				sp_manzeng_rule smr
			WHERE
				smr.id = smg.rule_id
			AND smr.del_flag = 'F'
		) smgs ON smgs.good_sku_id = pgs.id
		AND smgs.del_flag = 'F'
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
  where 1=1 and pgs.status ='1' and not EXISTS (
select 1 from sp_presell_good spg
left join sp_presell_activity spa on spa.id = spg.activity_id
where 1 = 1 and spg.sku_id = pgs.id AND spg.del_flag = 'F' and spa.presell_end > sysdate()
)
  <#if classifyId?? && classifyId!="">
    and (${classifyId})
  </#if>
  <#if cargoNo?? && cargoNo!="">
    and c.`cargo_no` = '${cargoNo}'
  </#if>
  <#if goodName?? && goodName != "">
    and (cs.name like concat('%', '${goodName}' ,'%') or cs.code like concat('%', '${goodName}' ,'%') or pg.pur_goods_name like concat('%', '${goodName}' ,'%'))
  </#if>
  <#if ruleIds?? && ruleIds!="">
    and smgs.rule_id = ${ruleIds}
  </#if>
	group by pgs.id order by pg.order_index desc
<#else>
 SELECT
    tgs.id,
    cs.code as skuId,
    tgs.good_id as goodId,
    tg.`name`,
    cs.cover_img as image,
    (IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0)) AS u8_stock,
    TRUNCATE(cs.retail_price,2) as retail_price,
    TRUNCATE((cs.retail_price - 10 - csp.referencePrice)* 0.93,2) as referencePrice
  FROM
    trade_good_sku tgs
  LEFT JOIN trade_good tg ON tg.id = tgs.good_id
  LEFT JOIN cargo_sku cs ON cs.id = tgs.cargo_sku_id
  LEFT JOIN cargo_sku_stock css ON css.cargo_sku_id = tgs.cargo_sku_id
  LEFT JOIN cargo c ON c.id = cs.cargo_id
	LEFT JOIN (
			SELECT smg.*
			FROM
			    sp_manzeng_good smg,
				sp_manzeng_rule smr
			WHERE
				smr.id = smg.rule_id
			AND smr.del_flag = 'F'
		) smgs ON smgs.good_sku_id = tgs.id
		AND smgs.del_flag = 'F'
  LEFT JOIN (
    SELECT
      cssp.cargo_sku_id,
      max(cssp.supply_price) as referencePrice
    FROM
      cargo_sku_supply_price cssp
  LEFT JOIN cargo_sku on cargo_sku.id = cssp.cargo_sku_id
    WHERE
      cssp.cargo_sku_id = cargo_sku.id
     group by cssp.cargo_sku_id
  ) csp on csp.cargo_sku_id = cs.id
  where 1=1 and tgs.status = '1'  and not EXISTS (
select 1 from sp_presell_good spg
left join sp_presell_activity spa on spa.id = spg.activity_id
where 1 = 1 and spg.sku_id = tgs.id AND spg.del_flag = 'F' and spa.presell_end > sysdate()
)
  <#if classifyId?? && classifyId!="">
    and ${classifyId}
  </#if>
  <#if cargoNo?? && cargoNo!="">
    and c.`cargo_no` = '${cargoNo}'
  </#if>
  <#if goodName?? && goodName != "">
    and (cs.name like concat('%', '${goodName}' ,'%') or cs.code like concat('%', '${goodName}' ,'%') or tg.name like concat('%', '${goodName}' ,'%'))
  </#if>
  <#if ruleIds?? && ruleIds!="">
    and smgs.rule_id = ${ruleIds}
  </#if>
	group by tgs.id order by tg.sort desc
</#if>
