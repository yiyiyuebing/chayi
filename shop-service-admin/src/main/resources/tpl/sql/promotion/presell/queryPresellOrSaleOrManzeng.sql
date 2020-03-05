<#if type?? && type != ''>
  <#if type == 'manzeng'>
    SELECT
    sp_manzeng_activity.`name` AS name,
    sp_manzeng_good.good_sku_id AS skuId,
    sp_manzeng_rule.man AS man,
    sp_manzeng_rule.jian_num AS jianNum,
    sp_manzeng_rule.zeng_num AS zengNum,
    sp_manzeng_good.max_num AS maxNum,
    NULL as discount,
    NULL AS presellAmount,
    sp_manzeng_activity.discount_type,
    'manzeng' as type
    FROM
    sp_manzeng_good
    LEFT JOIN sp_manzeng_rule ON sp_manzeng_rule.id=sp_manzeng_good.rule_id
    LEFT JOIN sp_manzeng_activity ON sp_manzeng_rule.activity_id=sp_manzeng_activity.id
    WHERE 1=1
    AND sp_manzeng_good.del_flag='F' AND sp_manzeng_activity.del_flag='F' AND sp_manzeng_rule.del_flag='F' AND sp_manzeng_activity.end_time>SYSDATE()
    <#if skuId?? && skuId!="" >
      AND sp_manzeng_good.good_sku_id="${skuId}"
    </#if>
    <#if goodSkuIds?? && goodSkuIds!="" >
      AND sp_manzeng_good.good_sku_id in (${goodSkuIds})
    </#if>
    <#if orderBizType?? && orderBizType != "" >
      AND sp_manzeng_activity.order_biz_type="${orderBizType}"
    </#if>
    <#if otherActivityIds?? && otherActivityIds != "" >
      AND sp_manzeng_activity.id not in(${otherActivityIds})
    </#if>
  <#elseif type == 'presell'>
    SELECT
    sp_presell_activity.`name` AS name,
    sp_presell_good.sku_id AS skuId,
    NULL AS man,
    NULL AS jianNum,
    NULL AS zengNum,
    NULL AS maxNum,
    NULL AS discount,
    sp_presell_good.presell_amount AS presellAmount,
    '' as discount_type,
    'presell' as type
    FROM
    sp_presell_activity
    LEFT JOIN sp_presell_good ON sp_presell_good.activity_id = sp_presell_activity.id
    WHERE 1=1
    AND sp_presell_good.del_flag='F' AND sp_presell_activity.del_flag='F' AND sp_presell_activity.presell_end > SYSDATE()
    <#if skuId?? && skuId!="" >
      AND sp_presell_good.sku_id="${skuId}"
    </#if>
    <#if goodSkuIds?? && goodSkuIds!="" >
      AND sp_presell_good.sku_id in (${goodSkuIds})
    </#if>
    <#if orderBizType?? && orderBizType != "" >
      AND sp_presell_activity.order_biz_type="${orderBizType}"
    </#if>
  <#else>
    SELECT
    sp_sale_activity.`name` AS name,
    sp_sale_activity_good.good_sku_id AS skuId,
    NULL AS man,
    NULL AS jianNum,
    NULL AS zengNum,
    NULL AS maxNum,
    sp_sale_activity_good.discount AS discount,
    NULL AS presellAmount,
    '' as discount_type,
    'sale' as type
    FROM
    sp_sale_activity
    LEFT JOIN sp_sale_activity_good ON sp_sale_activity.id=sp_sale_activity_good.activity_id
    WHERE 1=1
    AND sp_sale_activity_good.del_flag='F' AND sp_sale_activity.del_flag='F' AND sp_sale_activity.end_time > SYSDATE()
    <#if skuId?? && skuId!="" >
      AND sp_sale_activity_good.good_sku_id="${skuId}"
    </#if>
    <#if goodSkuIds?? && goodSkuIds!="" >
      AND sp_sale_activity_good.good_sku_id in (${goodSkuIds})
    </#if>
    <#if orderBizType?? && orderBizType != "" >
      AND  sp_sale_activity.order_biz_type="${orderBizType}"
    </#if>
  </#if>
<#else>
  (select
	sp_manzeng_activity.`name` AS name,
    sp_manzeng_activity_apply.good_ids AS skuId,
    NULL AS man,
    NULL AS jianNum,
    NULL AS zengNum,
    NULL AS maxNum,
    NULL as discount,
    NULL AS presellAmount,
    'manzeng' as type
  from sp_manzeng_activity_apply
  left join sp_manzeng_activity on sp_manzeng_activity.id = sp_manzeng_activity_apply.activity_id
  where 1=1
  AND sp_manzeng_activity.del_flag='F' AND sp_manzeng_activity.end_time>SYSDATE()
  <#if goodSkuIdList??>
      AND (
        <#list goodSkuIdList as goodSkuId>
            <#if goodSkuId_index gt 0>
                or
            </#if>
                find_in_set("${goodSkuId}", sp_manzeng_activity_apply.good_ids)
        </#list>
        )
    </#if>
  <#if orderBizType?? && orderBizType != "" >
    AND sp_manzeng_activity.order_biz_type="${orderBizType}"
  </#if>
  )
  UNION ALL
  (SELECT
  sp_presell_activity.`name` AS name,
  sp_presell_good.sku_id AS skuId,
  NULL AS man,
  NULL AS jianNum,
  NULL AS zengNum,
  NULL AS maxNum,
  NULL AS discount,
  sp_presell_good.presell_amount AS presellAmount,
  'presell' as type
  FROM
  sp_presell_activity
  LEFT JOIN sp_presell_good ON sp_presell_good.activity_id = sp_presell_activity.id
  WHERE 1=1
  AND sp_presell_good.del_flag='F' AND sp_presell_activity.del_flag='F' AND sp_presell_activity.presell_end > SYSDATE()
  <#if skuId?? && skuId!="" >
    AND sp_presell_good.sku_id="${skuId}"
  </#if>
  <#if goodSkuIds?? && goodSkuIds!="" >
      AND sp_presell_good.sku_id in (${goodSkuIds})
    </#if>
  <#if orderBizType?? && orderBizType != "" >
    AND sp_presell_activity.order_biz_type="${orderBizType}"
  </#if>

  )
  UNION ALL
  (SELECT
  sp_sale_activity.`name` AS name,
  sp_sale_activity_good.good_sku_id AS skuId,
  NULL AS man,
  NULL AS jianNum,
  NULL AS zengNum,
  NULL AS maxNum,
  sp_sale_activity_good.discount AS discount,
  NULL AS presellAmount,
  'sale' as type
  FROM
  sp_sale_activity
  LEFT JOIN sp_sale_activity_good ON sp_sale_activity.id=sp_sale_activity_good.activity_id
  WHERE 1=1
  AND sp_sale_activity_good.del_flag='F' AND sp_sale_activity.del_flag='F' AND sp_sale_activity.end_time > SYSDATE()
  <#if skuId?? && skuId!="" >
    AND sp_sale_activity_good.good_sku_id="${skuId}"
  </#if>
  <#if goodSkuIds?? && goodSkuIds!="" >
      AND sp_sale_activity_good.good_sku_id in (${goodSkuIds})
    </#if>
  <#if orderBizType?? && orderBizType != "" >
    AND  sp_sale_activity.order_biz_type="${orderBizType}"
  </#if>
  )
</#if>












