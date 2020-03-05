<#if purchseOrTrade == 'trade'>
SELECT
cs.cover_img AS groupId,
tgs.id AS skuId,
cs.name AS skuName
FROM
trade_good_sku tgs
LEFT JOIN
cargo_sku cs ON tgs.cargo_sku_id=cs.id
ORDER BY tgs.sale_num DESC LIMIT 0,9
 </#if>

 <#if purchseOrTrade == 'purchase'>
SELECT
cs.cover_img AS groupId,
tgs.id AS skuId,
cs.name AS skuName
FROM
purchase_goods_sku tgs
LEFT JOIN
cargo_sku cs ON tgs.cargo_sku_id=cs.id
ORDER BY tgs.sale_num DESC LIMIT 0,9
 </#if>