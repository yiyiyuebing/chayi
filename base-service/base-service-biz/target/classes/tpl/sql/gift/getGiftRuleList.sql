select tgr.*,
<#if orderBizType=='purchase'>
pg.pur_goods_name as gift_name
</#if>
<#if orderBizType=='trade'>
pg.name as gift_name
</#if>
from trade_gift_rule tgr
<#if orderBizType=='purchase'>
join purchase_goods pg on pg.id = tgr.gift_id
</#if>
<#if orderBizType=='trade'>
join trade_good pg on pg.id = tgr.gift_id
</#if>
where 1=1
<#if goodSkuId?? && goodSkuId !=''>
and tgr.good_sku_id = ${goodSkuId}
</#if>

