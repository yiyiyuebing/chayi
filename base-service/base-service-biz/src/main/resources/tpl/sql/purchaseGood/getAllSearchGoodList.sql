SELECT
	*
FROM
	purchase_goods_search
WHERE
pur_goods_name LIKE "%${query.purGoodsName}%"
OR pur_subtitle LIKE "%${query.purGoodsName}%"
OR brand_name LIKE "%${query.purGoodsName}%"
OR label_name LIKE "%${query.purGoodsName}%"
<#if query.classifyIds?? && query.classifyIds != "">
  OR classify_ids IN (${query.classifyIds})
</#if>
ORDER BY
<#if query.storeLevelId?? && query.storeLevelId != "">
  <#if query.storeLevelId == "267371146347261952">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        zy_delivery_price desc
      <#elseif query.priceSort=="1">
        zy_delivery_price asc
      </#if>
    </#if>
  <#elseif query.storeLevelId == "267371179276472320">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        jms_delivery_price desc
      <#elseif query.priceSort=="1">
        jms_delivery_price asc
      </#if>
    </#if>
  <#elseif query.storeLevelId == "267371213036695552">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        lms_delivery_price desc
      <#elseif query.priceSort=="1">
        lms_delivery_price asc
      </#if>
    </#if>
  <#elseif query.storeLevelId == "267371242152202240">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        yg_delivery_price desc
      <#elseif query.priceSort=="1">
        yg_delivery_price asc
      </#if>
    </#if>
  <#elseif query.storeLevelId == "300711581773840384">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        ts_delivery_price desc
      <#elseif query.priceSort=="1">
        ts_delivery_price asc
      </#if>
    </#if>
  </#if>
<#else>
  <#if query.priceSort?? && query.priceSort!="">
    <#if query.priceSort=="0">
      jms_delivery_price desc
    <#elseif query.priceSort=="1">
      jms_delivery_price asc
    </#if>
  </#if>
</#if>
<#if query.saleNumSort?? && query.saleNumSort!="">
  <#if query.saleNumSort=="0">
    sale_num desc
  </#if>
  <#if query.saleNumSort=="1">
    sale_num asc
  </#if>
</#if>
<#if query.orderIndex?? && query.orderIndex!="">
  <#if query.orderIndex=="0">
    order_index desc
  </#if>
  <#if query.orderIndex=="1">
     order_index asc
  </#if>
</#if>

LIMIT ${(query.pageNum - 1) * query.pageSize},${query.pageSize}
