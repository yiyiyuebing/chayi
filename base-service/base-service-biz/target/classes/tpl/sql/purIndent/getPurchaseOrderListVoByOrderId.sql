select
il.id, il.pur_goods_name as purGoodsName, il.supply_price as  supplyPrice, il.final_amount as finalAmount,
il.status as status, il.pur_goods_amount as purGoodsAmount, il.pur_goods_type as purGoodsType, il.pur_packing_price as purPackingPrice,
il.pur_packing_bag_value as purPackingBagValue, il.packing_style_name as packingStyleName, il.packing_style_code,
cs.code as cargoNo, pgs.sample_code as sampleCode, il.pur_goods_id, il.cargo_sku_id as cargoSkuId,
il.is_sample as isSample, il.pur_goods_img_url as purGoodsImgUrl, il.material_sku_id as materialSkuId
from purchase_order_list il
left join cargo_sku cs on cs.id = il.material_sku_id
left join purchase_goods_sample pgs on pgs.pur_goods_id = il.pur_goods_id
where il.order_id = ?
<#if afterSaleService??> and il.status = '${afterSaleService}' </#if>
<#if goodSku??> and il.cargo_sku_id = '${goodSku}' </#if>
<#if cargoNo??> and (cs.code like concat('%', '${cargoNo ! ''}', '%') or pgs.sample_code like concat('%', '${cargoNo ! ''}', '%')) </#if>
;