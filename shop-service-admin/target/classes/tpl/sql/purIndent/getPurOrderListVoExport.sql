select
il.id,
il.pur_goods_name as purGoodsName,
sum(ifnull(il.supply_price, 0.00)) / sum(il.number) as supplyPrice,
sum(ifnull(il.final_amount, 0.00)) / sum(il.number) as finalAmount,
sum(il.number) as number,
il.status as status,
sum(ifnull(il.pur_goods_amount,0.00)) as purGoodsAmount,
il.pur_goods_type as purGoodsType,
il.pur_packing_price as purPackingPrice,
il.pur_packing_bag_value as purPackingBagValue,
il.packing_style_name as packingStyleName,
il.packing_style_code,
cs.code as cargoNo,
pgs.sample_code as sampleCode,
pgs.sample_sku as sampleSku,
il.pur_goods_id,
il.cargo_sku_id as cargoSkuId,
 il.pur_goods_sku_id as purGoodsSkuId,
il.is_sample as isSample, il.pur_goods_img_url as purGoodsImgUrl, il.gift_flag,
ifnull(polpe.presell_first, 0.00) as presellFirst,
ifnull(polpe.presell_end, 0.00) as presellEnd,
ifnull(polpe.presell_amount, 0.00) as presellAmount,
if (polpe.presell_end, 'second', 'one') as presllType,
cs.sku_name as skuName,
cs.name as cargoSkuName,
ifnull(cs.retail_price, 0.00) as retailPrice,
il.order_id
from purchase_order_list il
left join cargo_sku cs on cs.id = il.cargo_sku_id
left join purchase_goods_sample pgs on pgs.pur_goods_id = il.pur_goods_id
left join purchase_order_list_presell_extra polpe on polpe.order_id = il.order_id and polpe.good_sku_id = il.pur_goods_sku_id
where 1=1
<#if ids?? && ids != ''>
and il.order_id in (${ids})
</#if>
group by il.order_id, il.pur_goods_sku_id;