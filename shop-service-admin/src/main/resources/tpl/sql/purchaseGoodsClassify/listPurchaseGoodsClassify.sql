select
pcy.*, classifyStoreLevel.storeLevelName
from
purchase_classify pcy
left join (select ppc.id, (select group_concat(sll.name) from store_level sll where find_in_set(sll.level_Id , ppc.store_level) and sll.statue = 1) as storeLevelName from purchase_classify ppc) classifyStoreLevel on classifyStoreLevel.id = pcy.id
where 1=1 and pcy.del_flag = 'F'
<#if parentId??>
 and pcy.parent_id= '${parentId}'
</#if>
<#if status??>
 and pcy.status= ${status}
</#if>
order by pcy.order_index desc;