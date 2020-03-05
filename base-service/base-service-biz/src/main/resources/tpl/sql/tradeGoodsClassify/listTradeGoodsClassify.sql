select
pcy.*
from
trade_goods_classify pcy
where 1=1 and pcy.del_flag = 'F'
<#if parentId??>
 and pcy.parent_id= '${parentId}'
</#if>
<#if status??>
 and pcy.status= ${status}
</#if>
order by pcy.order_index desc;