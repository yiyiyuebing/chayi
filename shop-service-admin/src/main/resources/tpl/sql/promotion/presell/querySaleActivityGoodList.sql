
 <#if orderBizType == 'trade'>
SELECT
ssa.id ,
ssa.`name` AS `name`,
ssa.memo AS memo,
ssa.start_time AS startTime,
ssa.end_time AS endTime,
ssag.good_sku_id AS goodSkuId,
COUNT(ssag.activity_id) AS maxNum,
(select pic_url from image where group_id=cs.cover_img limit 1) as image,
ssa.is_valid AS isValid
FROM
sp_sale_activity ssa
LEFT JOIN sp_sale_activity_good ssag ON ssa.id =ssag.activity_id
LEFT JOIN trade_good_sku tgs ON tgs.id  = ssag.good_sku_id
LEFT JOIN cargo_sku cs ON cs.id =tgs.cargo_sku_id
LEFT JOIN trade_good tg ON tg.id =tgs.good_id
WHERE 1=1 AND ssa.del_flag='F'
<#if activityName?? && activityName !="">
  AND ssa.`name` LIKE  "%${activityName}%"         <#----活动名称--->
</#if>
<#if goodSkuId?? && goodSkuId !="">
  AND cs.`code` ="${goodSkuId}"          <#----sku编码--->
</#if>
<#if goodTitle?? && goodTitle !="">
  AND tg.`name` LIKE  "%${goodTitle}%"         <#----商品标题--->
</#if>
<#if orderBizType?? && orderBizType !="">
  AND ssa.order_biz_type="${orderBizType}"                <#----商城或者是采购--->
</#if>
 <#if activityHaveStart == 'now'>    <#---正在进行---->
   AND ssa.start_time <= SYSDATE() AND ssa.end_time >= SYSDATE()
 </#if>
  <#if activityHaveStart == 'wait'>          <#---未开始进行---->
   AND ssa.start_time >= SYSDATE()
 </#if>
  <#if activityHaveStart == 'over'>           <#---已结束---->
  AND ssa.end_time <= SYSDATE()
 </#if>
  GROUP BY ssa.id   ORDER BY ssa.is_valid DESC, ssa.start_time DESC,ssa.date_created DESC
 </#if>

 <#-------------------------------------------------------------------------分割线---------------------------------------->

 <#if orderBizType == 'purchase'>
SELECT
ssa.id,
ssa.`name` AS `name`,
ssa.memo AS memo,
ssa.start_time AS startTime,
ssa.end_time AS endTime,
ssag.good_sku_id AS goodSkuId,
COUNT(ssag.activity_id) AS maxNum,
(select pic_url from image where group_id=cs.cover_img limit 1) as image,
ssa.is_valid AS isValid
FROM
sp_sale_activity ssa
LEFT JOIN sp_sale_activity_good ssag ON ssa.id=ssag.activity_id
LEFT JOIN purchase_goods_sku tgs ON tgs.id=ssag.good_sku_id
LEFT JOIN cargo_sku cs ON cs.id=tgs.cargo_sku_id
LEFT JOIN purchase_goods tg ON tg.id=tgs.pur_goods_id
WHERE 1=1 AND ssa.del_flag='F'
<#if activityName?? && activityName !="">
  AND ssa.`name` LIKE  "%${activityName}%"         <#----活动名称--->
</#if>
<#if goodSkuId?? && goodSkuId !="">
  AND cs.`code` ="${goodSkuId}"          <#----sku编码--->
</#if>
<#if goodTitle?? && goodTitle !="">
  AND tg.pur_goods_name LIKE  "%${goodTitle}%"         <#----商品标题--->
</#if>
<#if orderBizType?? && orderBizType !="">
  AND ssa.order_biz_type="${orderBizType}"                <#----商城或者是采购--->
</#if>
 <#if activityHaveStart == 'now'>    <#---正在进行---->
   AND ssa.start_time <= SYSDATE() AND ssa.end_time >= SYSDATE()
 </#if>
  <#if activityHaveStart == 'wait'>          <#---未开始进行---->
   AND ssa.start_time >= SYSDATE()
 </#if>
  <#if activityHaveStart == 'over'>           <#---已结束---->
  AND ssa.end_time <= SYSDATE()
 </#if>
  GROUP BY ssa.id   ORDER BY ssa.is_valid DESC, ssa.start_time DESC,ssa.date_created DESC

 </#if>