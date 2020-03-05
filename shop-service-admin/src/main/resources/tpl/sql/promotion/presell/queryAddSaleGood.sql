 <#if orderBizType == 'purchase'>
SELECT
ssag.activity_id AS activityId,
ssag.sale_type AS saleType,
ssag.discount AS disCount,
ssag.activity_price AS activityMoney,
ssag.vm_num AS vmNum,
ssag.buy_num AS buyNum,
ssag.max_num AS maxNum,
cs.cover_img AS groupId,
pg.pur_goods_name AS name,
pg.pur_subtitle AS goodDesc,
pgs.pur_goods_id as goodId,
pgs.cargo_sku_name AS cargoSkuName,
pgs.id,
cs.`code` AS skuId,
cssp.retail_price AS purchaseOnePrice,
TRUNCATE(cssp.referencePrice, 2) AS retailPrice,
TRUNCATE((cs.retail_price-10-cssp.referencePrice)*0.93,2) AS referencePrice,
		CASE
		WHEN spsag.id IS NOT NULL THEN
			'TT'
		END AS isJoin
FROM
purchase_goods_sku pgs
LEFT JOIN sp_sale_activity_good ssag ON pgs.id=ssag.good_sku_id
LEFT JOIN purchase_goods pg ON pgs.pur_goods_id=pg.id
LEFT JOIN cargo_sku cs ON cs.id=pgs.cargo_sku_id
LEFT JOIN cargo c ON cs.cargo_id=c.id
LEFT JOIN
(SELECT cssp.cargo_sku_id,cs.retail_price,MAX(cssp.supply_price) AS referencePrice FROM cargo_sku_supply_price cssp LEFT JOIN cargo_sku cs ON cs.id=cssp.cargo_sku_id
WHERE cs.id=cssp.cargo_sku_id AND cssp.store_level_id IN
(SELECT sl.level_Id FROM store_level sl WHERE sl.name IN ('加盟商','联盟商','特殊加盟'))
GROUP BY cssp.cargo_sku_id) cssp ON cssp.cargo_sku_id=cs.id
LEFT JOIN
(SELECT ssag.*  FROM sp_sale_activity ssa,sp_sale_activity_good ssag
WHERE ssag.activity_id=ssa.id
AND ssa.end_time>SYSDATE()
AND ssa.del_flag='F') spsag ON spsag.good_sku_id=pgs.id AND spsag.del_flag='F'
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
    GROUP BY pgs.id
 order by ssag.activity_id is not null and ssag.activity_id!=''  desc,pg.order_index DESC
<#else>
SELECT
ssag.activity_id AS activityId,
ssag.sale_type AS saleType,
ssag.discount AS disCount,
ssag.activity_price AS activityMoney,
ssag.vm_num AS vmNum,
ssag.buy_num AS buyNum,
ssag.max_num AS maxNum,
cs.cover_img AS groupId,
tg.`name` AS name,
tgs.good_id as goodId,
tgs.cargo_sku_name AS cargoSkuName,
tg.subtitle AS goodDesc,
tgs.id,
cs.`code` AS skuId,
TRUNCATE(cs.retail_price,2) AS retailPrice,
TRUNCATE((cs.retail_price-10-cssp.referencePrice)*0.93,2) AS referencePrice,
		CASE
		WHEN spsag.id IS NOT NULL THEN
			'TT'
		END AS isJoin
FROM
trade_good_sku tgs
LEFT JOIN sp_sale_activity_good ssag ON tgs.id=ssag.good_sku_id
LEFT JOIN trade_good tg ON tgs.good_id=tg.id
LEFT JOIN cargo_sku cs ON cs.id=tgs.cargo_sku_id
LEFT JOIN cargo c ON cs.cargo_id=c.id
LEFT JOIN
(SELECT cssp.cargo_sku_id,MAX(cssp.supply_price) AS referencePrice FROM cargo_sku_supply_price cssp LEFT JOIN cargo_sku cs ON cs.id=cssp.cargo_sku_id
WHERE cs.id=cssp.cargo_sku_id AND cssp.store_level_id IN
(SELECT sl.level_Id FROM store_level sl WHERE sl.name IN ('加盟商','联盟商','特殊加盟'))
GROUP BY cssp.cargo_sku_id) cssp ON cssp.cargo_sku_id=cs.id
LEFT JOIN
(SELECT ssag.*  FROM sp_sale_activity ssa,sp_sale_activity_good ssag
WHERE ssag.activity_id=ssa.id
AND ssa.end_time>SYSDATE()
AND ssa.del_flag='F') spsag ON spsag.good_sku_id=tgs.id AND spsag.del_flag='F'
 where 1=1 and tgs.status ='1'
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
  GROUP BY tgs.id order by ssag.activity_id is not null and ssag.activity_id!=''  desc,tg.sort DESC
</#if>

