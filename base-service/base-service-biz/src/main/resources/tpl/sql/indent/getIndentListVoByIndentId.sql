select
il.id, il.trade_good_name as tradeGoodName, il.supply_price as  supplyPrice, il.final_amount as finalAmount,
il.status as status,
il.gift_flag as giftFlag, il.trade_good_img_url as tradeGoodImgUrl, cs.code as cargoNo, il.cargo_sku_id as cargoSkuId
from indent_list il
left join cargo_sku cs on cs.id = il.cargo_sku_id
where il.indent_id = ?
<#if afterSaleService??> and il.status = '${afterSaleService}' </#if>
<#if goodSku??> and il.cargo_sku_id = '${goodSku}' </#if>
<#if cargoNo??> and cs.code like concat('%', '${cargoNo ! ''}', '%') </#if>
;



