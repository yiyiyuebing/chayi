<#if orderBizType?? && orderBizType == 'trade'>
select tgs.id as goodSkuId from trade_good_sku tgs
left join trade_good tg on tg.id = tgs.good_id
left join cargo_sku cs on cs.id = tgs.cargo_sku_id
where 1=1
    <#if skuCode?? && skuCode != ''>
    and cs.code like concat('%', '${skuCode}', '%')
    </#if>
    <#if goodName?? && goodName != ''>
    and tg.name like concat('%', '${goodName}', '%')
    </#if>
<#else>
select tgs.id as goodSkuId from purchase_goods_sku tgs
left join purchase_goods tg on tg.id = tgs.pur_goods_id
left join cargo_sku cs on cs.id = tgs.cargo_sku_id
where 1=1
    <#if skuCode?? && skuCode != ''>
    and cs.code like concat('%', '${skuCode}', '%')
    </#if>
    <#if goodName?? && goodName != ''>
    and tg.pur_goods_name like concat('%', '${goodName}', '%')
    </#if>
</#if>
;