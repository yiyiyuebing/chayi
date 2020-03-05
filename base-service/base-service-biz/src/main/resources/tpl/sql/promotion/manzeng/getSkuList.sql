<#if orderBizType == "purchase">
  SELECT
    g.id AS id,
    g.pur_goods_name AS NAME,
    s.id AS sku_id,
    s.cargo_sku_name AS sku_name,
    g.group_id AS classify_ids
  FROM
    purchase_goods g
  JOIN cargo c ON c.id = g.cargo_id AND c.is_sancha = "F"
  JOIN purchase_goods_sku s ON g.id = s.pur_goods_id
  <#if !isSelectAll?? || isSelectAll != "T">
      WHERE 1 = 2
      <#if classifyIds?? && classifyIds?size gt 0>
          <#list classifyIds as classifyId>
               or find_in_set("${classifyId}", g.group_id)
          </#list>
      </#if>
      <#if skuIds?? && skuIds != "">
          or s.id in (${skuIds})
      </#if>
  </#if>
<#else>
  SELECT
    g.id as id,
    g.name as name,
    s.id as sku_id,
    s.cargo_sku_name as sku_name,
    g.group_id as classify_ids
  FROM
    trade_good g
  JOIN trade_good_sku s ON g.id = s.good_id
  <#if !isSelectAll?? || isSelectAll != "T">
      WHERE 1 = 2
      <#if classifyIds?? && classifyIds?size gt 0>
          <#list classifyIds as classifyId>
               or find_in_set("${classifyId}", g.group_id)
          </#list>
      </#if>
      <#if skuIds?? && skuIds != "">
          or s.id in (${skuIds})
      </#if>
  </#if>
</#if>