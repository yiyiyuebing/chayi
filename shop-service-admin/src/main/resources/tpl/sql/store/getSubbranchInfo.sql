select ss.* from store_subbranch ss
left join vtwo_store_role vsr on vsr.store_id = ss.id
where 1=1
<#if storeId?? && storeId?length gt 0>
    and ss.id = ${storeId}
</#if>