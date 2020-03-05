SELECT
ssa .`name` AS activityId,
ssag.discount,
ssag.activity_price AS activityPrice,
ssag.sale_type AS saleType
FROM
sp_sale_activity_good  ssag
LEFT JOIN sp_sale_activity ssa ON ssa.id=ssag.activity_id
WHERE 1=1 AND ssag.del_flag='F'
 <#if skuId?? && skuId!="">
    AND  ssag.good_sku_id="${skuId}"
  </#if>