SELECT
tgs.trade_good_id AS id,
tgs.trade_good_name AS name,
tgs.trade_subtitle AS subtitle,
tgs.fixed_price,
tgs.retail_price,
tgs.good_create_time AS createTime,
tgs.sale_num AS totalSaleNum,
tgs.trade_good_sku_id AS goodSkuId,
case tgs.label_name when "代销" then null else tgs.label_name end as label,
tgs.show_picture,
<#if info.storeLevelId?? && info.storeLevelId != "">
  <#if info.storeLevelId == "267371146347261952">
    tgs.zy_delivery_price AS supplyPrice,
  <#elseif info.storeLevelId == "267371179276472320">
    tgs.jms_delivery_price AS supplyPrice,
  <#elseif info.storeLevelId == "267371213036695552">
    tgs.lms_delivery_price AS supplyPrice,
  <#elseif info.storeLevelId == "267371242152202240">
    tgs.yg_delivery_price AS supplyPrice,
  <#elseif info.storeLevelId == "300711581773840384">
    tgs.ts_delivery_price AS supplyPrice,
  </#if>
</#if>
tgs.stock
FROM trade_good_search tgs
JOIN trade_good tg ON tgs.trade_good_id = tg.id
LEFT JOIN goods_column gc ON tgs.category = gc.id
WHERE tg.status = "1" AND (gc.showYn is null or gc.showYn="") AND tg.classify_valid = "T"
<#if info.category?? && info.category !="">
  AND tgs.category like "%${info.category}%"
</#if>
<#if info.theme?? && info.theme !="">
  AND tgs.theme like "%${info.theme}%"
</#if>
<#if info.startPrice?? && info.startPrice!="">
  AND tgs.retail_price >= ${info.startPrice}
</#if>
<#if info.endPrice?? && info.endPrice!="">
  AND tgs.retail_price <= ${info.endPrice}
</#if>
<#if info.classifyId?? && info.classifyId != "">
  AND tgs.group_id in (${info.classifyId})
</#if>
<#if info.brandId?? && info.brandId!="">
  AND tgs.brand_id = ${info.brandId}
</#if>
<#if info.goodName?? && info.goodName!="">
  AND tgs.trade_good_name like "%${info.goodName}%"
</#if>
<#if info.use?? && info.use != "">
  AND tgs.base_label_id like "%${info.use}%"
</#if>
ORDER BY
<#if info.saleNumSort?? && info.saleNumSort!="">
  <#if info.saleNumSort == "0">
    tgs.sale_num desc,
  </#if>
  <#if info.saleNumSort == "1">
    tgs.sale_num asc,
  </#if>
</#if>
<#if info.priceSort?? && info.priceSort!="">
  <#if info.priceSort == "0">
    tgs.retail_price desc,
  </#if>
  <#if info.priceSort == "1">
    tgs.retail_price asc,
  </#if>
</#if>
tgs.sort desc,tgs.good_create_time desc LIMIT ${((info.pageNum - 1) * info.pageSize)?c},${info.pageSize}