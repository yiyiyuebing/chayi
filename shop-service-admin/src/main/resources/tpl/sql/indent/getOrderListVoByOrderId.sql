select
il.id,
il.indent_id,
il.trade_good_name as tradeGoodName,
il.supply_price as  supplyPrice,
sum(ifnull(il.trade_good_amount,0.00)) / sum(il.number)  as goodAmount,
sum(ifnull(il.final_amount,0.00)) / sum(il.number)  as finalAmount,
sum(ifnull(il.final_amount, 0.00)) as totalFinalAmount,
sum(il.number) as number,
sum(ifnull(il.discount_amount, 0.00)) / sum(il.number) as discount_amount,
il.trade_good_sku_id,
il.status as status,
il.gift_flag as giftFlag,
il.trade_good_img_url as tradeGoodImgUrl,
cs.code as cargoNo,
il.cargo_sku_id as cargoSkuId,
cs.sku_name as skuName,
ifnull(tolpe.presell_first, 0.00) as presellFirst,
ifnull(tolpe.presell_end, 0.00) as presellEnd,
ifnull(tolpe.presell_amount, 0.00) as presellAmount,
if (tolpe.presell_end, 'second', 'one') as presllType
,cs.name as cargoSkuName
,il.good_type as goodType
,ifnull(cs.retail_price, 0.00) as retailPrice
from indent_list il
left join cargo_sku cs on cs.id = il.cargo_sku_id
left join trade_order_list_presell_extra tolpe on tolpe.good_sku_id = il.trade_good_sku_id and tolpe.order_id = il.indent_id
where 1=1
<#if ids??> and il.indent_id in (${ids}) </#if>
<#if afterSaleService?? && afterSaleService?length gt 0> and il.status in ('${afterSaleService}')   </#if>
<#if goodSku??> and il.trade_good_sku_id = '${goodSku}' </#if>
<#if cargoNo??> and cs.code like concat('%', '${cargoNo ! ''}', '%') </#if>
group by il.indent_id, il.trade_good_sku_id ORDER BY il.id ASC
;



