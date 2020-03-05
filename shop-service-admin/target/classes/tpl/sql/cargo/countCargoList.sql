select count(1) from (select
count(1)
from cargo cg
left join (select cargo_id, group_concat(name) skuName, group_concat(code) skuCode, group_concat(memo) skuMemo from cargo_sku group by cargo_id) cs on cs.cargo_id = cg.id
left join cargo_classify cc on cc.id = cg.classify_id
left join cargo_supplier csp on csp.id = cg.supplier_id
left join cargo_brand cb on cb.id = cg.brand_id
left join (
			select cargo_id, group_concat(`name`) specs
			from cargo_sku_type
			group by cargo_id) f on cg.id=f.cargo_id
where 1=1 and (cg.del_flag != 'T' or cg.del_flag is null)
<#if cargoName?? && cargoName != ''>
 and (
 cg.cargo_no like '%${cargoName}%'
 or cg.name like '%${cargoName}%'
 or cs.skuName like '%${cargoName}%'
 or cs.skuCode like '%${cargoName}%'
 or f.specs like '%${cargoName}%'
 or cs.skuMemo like '%${cargoName}%'
 )
</#if>
<#if classifyId?? && classifyId != ''>
 and cc.id in (${classifyId})
</#if>
<#if brandId?? && brandId != ''>
 and cb.id = ${brandId}
</#if>
group by cg.id) a