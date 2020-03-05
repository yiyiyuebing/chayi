select *
from purchase_goods pg where 1=1
<#if purClassifyId??>
   and pg.group_id like '%${purClassifyId}%'
</#if>
order by pg.create_time desc
limit ?, ?;