SELECT
	pgs.*,
	case pgs.label_name when "代销" then null else pgs.label_name end as label_name
FROM
	(SELECT * FROM purchase_goods_search WHERE find_in_set("${query.storeLevelId}", store_level)) pgs
	JOIN purchase_goods pg ON pg.id = pgs.pur_goods_id
	JOIN purchase_goods_sku gs ON gs.id = pgs.pur_goods_sku_id
WHERE
	pg.status = "1"
	AND gs.status = "1"
	AND pg.classify_valid = "T"
<#if query.keyword?? && query.keyword != "">
  AND (pgs.pur_goods_name LIKE "%${query.keyword}%"
  OR pgs.pur_subtitle LIKE "%${query.keyword}%"
  OR pgs.brand_name LIKE "%${query.keyword}%"
  OR pgs.label_name LIKE "%${query.keyword}%"
  <#list query.keywordClassifyList as classify>
    OR find_in_set("${classify}", pgs.classify_ids)
  </#list>
  )
</#if>
<#if query.purGoodsName?? && query.purGoodsName != "">
  AND pgs.pur_goods_name LIKE "%${query.purGoodsName}}%"
</#if>
<#if query.classifyList?? && query.classifyList?size gt 0>
  AND (
  <#list query.classifyList as classify>
    <#if classify_index gt 0>
      OR
    </#if>
    find_in_set("${classify}", pgs.classify_ids)
  </#list>
  )
</#if>
<#if query.brandIds?? && query.brandIds != "">
  AND pgs.brand_id IN (${query.brandIds})
</#if>
<#if query.classifyAttrIds?? && query.classifyAttrIds != "">
  AND pgs.classify_attr_ids IN (${query.classifyAttrIds})
</#if>
<#if query.storeLevelId == "267371146347261952">
  <#if query.minPrice?? && query.minPrice != "">
    AND pgs.zy_delivery_price >= ${query.minPrice}
  </#if>
  <#if query.maxPrice?? && query.maxPrice != "">
    AND pgs.zy_delivery_price <= ${query.maxPrice}
  </#if>
<#elseif query.storeLevelId == "267371179276472320">
  <#if query.minPrice?? && query.minPrice != "">
    AND pgs.jms_delivery_price >= ${query.minPrice}
  </#if>
  <#if query.maxPrice?? && query.maxPrice != "">
    AND pgs.jms_delivery_price <= ${query.maxPrice}
  </#if>
<#elseif query.storeLevelId == "267371213036695552">
  <#if query.minPrice?? && query.minPrice != "">
    AND pgs.lms_delivery_price >= ${query.minPrice}
  </#if>
  <#if query.maxPrice?? && query.maxPrice != "">
    AND pgs.lms_delivery_price <= ${query.maxPrice}
  </#if>
<#elseif query.storeLevelId == "267371242152202240">
  <#if query.minPrice?? && query.minPrice != "">
    AND pgs.yg_delivery_price >= ${query.minPrice}
  </#if>
  <#if query.maxPrice?? && query.maxPrice != "">
    AND pgs.yg_delivery_price <= ${query.maxPrice}
  </#if>
<#elseif query.storeLevelId == "300711581773840384">
  <#if query.minPrice?? && query.minPrice != "">
    AND pgs.ts_delivery_price >= ${query.minPrice}
  </#if>
  <#if query.maxPrice?? && query.maxPrice != "">
    AND pgs.ts_delivery_price <= ${query.maxPrice}
  </#if>
</#if>
ORDER BY
<#if query.storeLevelId?? && query.storeLevelId != "">
  <#if query.storeLevelId == "267371146347261952">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        pgs.zy_delivery_price desc,
      <#elseif query.priceSort=="1">
        pgs.zy_delivery_price asc,
      </#if>
    </#if>
  <#elseif query.storeLevelId == "267371179276472320">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        pgs.jms_delivery_price desc,
      <#elseif query.priceSort=="1">
        pgs.jms_delivery_price asc,
      </#if>
    </#if>
  <#elseif query.storeLevelId == "267371213036695552">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        pgs.lms_delivery_price desc,
      <#elseif query.priceSort=="1">
        pgs.lms_delivery_price asc,
      </#if>
    </#if>
  <#elseif query.storeLevelId == "267371242152202240">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        pgs.yg_delivery_price desc,
      <#elseif query.priceSort=="1">
        pgs.yg_delivery_price asc,
      </#if>
    </#if>
  <#elseif query.storeLevelId == "300711581773840384">
    <#if query.priceSort?? && query.priceSort!="">
      <#if query.priceSort=="0">
        pgs.ts_delivery_price desc,
      <#elseif query.priceSort=="1">
        pgs.ts_delivery_price asc,
      </#if>
    </#if>
  </#if>
<#else>
  <#if query.priceSort?? && query.priceSort!="">
    <#if query.priceSort=="0">
      pgs.jms_delivery_price desc,
    <#elseif query.priceSort=="1">
      pgs.jms_delivery_price asc,
    </#if>
  </#if>
</#if>
<#if query.saleNumSort?? && query.saleNumSort!="">
  <#if query.saleNumSort=="0">
    pgs.sale_num desc,
  </#if>
  <#if query.saleNumSort=="1">
    pgs.sale_num asc,
  </#if>
</#if>
<#if query.orderIndex?? && query.orderIndex!="">
  <#if query.orderIndex=="0">
    pgs.order_index desc,
  </#if>
  <#if query.orderIndex=="1">
     pgs.order_index asc,
  </#if>
</#if>
<#if query.createTimeSort?? && query.createTimeSort!="">
  <#if query.createTimeSort=="0">
    pgs.good_create_time desc,
  </#if>
  <#if query.createTimeSort=="1">
     pgs.good_create_time asc,
  </#if>
</#if>
  pgs.order_index desc
LIMIT ${((query.pageNum - 1) * query.pageSize)?c},${query.pageSize}
