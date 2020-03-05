select
il.id,
il.order_id as orderId,
il.pur_goods_name as purGoodsName,
ifnull(il.supply_price, 0.00) as  supplyPrice,
sum(ifnull(il.final_amount, 0.00)) / sum(il.number) as finalAmount,
sum(ifnull(il.final_amount, 0.00)) as totalFinalAmount,
sum(il.number) as number,
il.status as status,
ifnull(il.pur_goods_amount, 0.00) as purGoodsAmount,
sum(ifnull(il.pur_goods_amount,0.00)) / sum(il.number)  as goodAmount,
sum(ifnull(il.discount_amount, 0.00)) as discount_amount,
il.pur_goods_type as purGoodsType,
ifnull(il.pur_packing_price, 0.00) as purPackingPrice,
il.pur_packing_bag_value as purPackingBagValue,
il.packing_style_name as packingStyleName,
il.packing_style_code,
cs.code as cargoNo,
pgs.sample_code as sampleCode,
pgs.sample_sku as sampleSku,
il.pur_goods_id, il.cargo_sku_id as cargoSkuId,
il.pur_goods_sku_id as purGoodsSkuId,
il.is_sample as isSample,
il.pur_goods_img_url as purGoodsImgUrl,
il.material_sku_id as materialSkuId,
il.gift_flag,
ifnull(polpe.presell_first, 0.00) as presellFirst,
ifnull(polpe.presell_end, 0.00) as presellEnd,
ifnull(polpe.presell_amount, 0.00) as presellAmount,
if (polpe.presell_end, 'second', 'one') as presllType,
cs.sku_name as skuName,
cs.name as cargoSkuName,
il.good_type as goodType,
ifnull(cs.retail_price, 0.00) as retailPrice
from purchase_order_list il
left join cargo_sku cs on cs.id = il.cargo_sku_id
left join purchase_goods_sample pgs on pgs.pur_goods_id = il.pur_goods_id
left join purchase_order_list_presell_extra polpe on polpe.order_id = il.order_id and polpe.good_sku_id = il.pur_goods_sku_id
where 1=1
<#if ids??>
and il.order_id in (${ids})
<#else>
and il.order_id = ?
</#if>
<#if afterSaleService??> and il.status in ('${afterSaleService}') </#if>
<#if goodSku??> and il.pur_goods_sku_id = '${goodSku}' </#if>
<#if cargoNo??> and (cs.code like concat('%', '${cargoNo ! ''}', '%') or pgs.sample_code like concat('%', '${cargoNo ! ''}', '%')) </#if>
group by il.order_id, il.pur_goods_sku_id ORDER BY il.id ASC;