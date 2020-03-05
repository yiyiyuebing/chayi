select
cg.id, cg.cargo_no, cg.name, cc.name as classifyName,
cb.name as brandName, csp.name as supplierName, cs.skuCode,
cs.skuName, cs.skuMemo, f.specs, cg.pc_album_id as pcAlbumId
from cargo cg
left join (select cargo_id, group_concat(sku_name) skuName, group_concat(code) skuCode, group_concat(memo) skuMemo from cargo_sku group by cargo_id) cs on cs.cargo_id = cg.id
left join cargo_classify cc on cc.id = cg.parent_classify_id
left join cargo_supplier csp on csp.id = cg.supplier_id
left join cargo_brand cb on cb.id = cg.brand_id
left join (
			select cargo_id, group_concat(`name`) specs
			from cargo_sku_type
			group by cargo_id) f on cg.id=f.cargo_id
where 1=1 and cg.del_flag != 'T'
<#if name?? && name != ''>
and (
cg.cargo_no like '%base-service-biz%'
 or cg.name like '%base-service-biz%'
 or cs.skuName like '%base-service-biz%'
 or cs.skuCode like '%base-service-biz%'
 or f.specs like '%base-service-biz%'
 or cs.skuMemo like '%base-service-biz%'
 )
</#if>
<#if classifyId?? && classifyId != ''>
 and cc.id = ${classifyId}
</#if>
<#if brandId?? && brandId != ''>
 and cb.id = ${brandId}
</#if>
group by cg.id
order by cg.create_time desc limit ?, ?;