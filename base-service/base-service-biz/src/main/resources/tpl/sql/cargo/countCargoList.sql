select count(*) from (
select count(*)
from cargo cg
left join (select cargo_id, group_concat(sku_name) skuName, group_concat(code) skuCode, group_concat(memo) skuMemo from cargo_sku group by cargo_id) cs on cs.cargo_id = cg.id
left join cargo_classify cc on cc.id = cg.parent_classify_id
left join cargo_supplier csp on csp.id = cg.supplier_id
left join image i on i.group_id = cg.mobile_album_id
left join cargo_brand cb on cb.id = cg.brand_id
left join (
			select cargo_id, group_concat(`name`) specs
			from cargo_sku_type
			group by cargo_id) f on cg.id=f.cargo_id
where 1=1 and cg.del_flag != 'T'
<#if name??>
 and (
 cg.cargo_no like '%${name}%'
 or cg.name like '%${name}%'
 or cs.skuName like '%${name}%'
 or cs.skuCode like '%${name}%'
 or f.specs like '%${name}%'
 or cs.skuMemo like '%${name}%'
 )
</#if>
<#if classifyId?? && classifyId != ''>
 and cc.id = ${classifyId}
</#if>
<#if brandId?? && brandId != ''>
 and cb.id = ${brandId}
</#if>
group by cg.id) a