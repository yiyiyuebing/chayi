(select
tgs.id as skuId, spa.id as activity_id, 'presell' as type, spa.presell_end as end_time,
spg.first_amount, spg.presell_amount, spg.presell_num, spg.vm_count
from sp_presell_good spg
<#if orderBizType?? && orderBizType == 'trade'>
left join trade_good_sku tgs on spg.sku_id = tgs.id
<#else>
left join purchase_goods_sku tgs on spg.sku_id = tgs.id
</#if>
left join sp_presell_activity spa on spa.id = spg.activity_id where 1=1 and tgs.id is not null and spa.del_flag ='F')
union
(select tgs.id as skuId, ssa.id as activity_id, 'sale' as type, ssa.end_time,
0 as first_amount, 0 as presell_amount, 0 as presell_num, 0 as vm_count
from sp_sale_activity_good ssag
<#if orderBizType?? && orderBizType == 'trade'>
left join trade_good_sku tgs on tgs.id = ssag.good_sku_id
<#else>
left join purchase_goods_sku tgs on tgs.id = ssag.good_sku_id
</#if>
left join sp_sale_activity ssa on ssa.id = ssag.activity_id where 1=1 and tgs.id is not null and ssa.del_flag ='F');

