select
ms.id, ms.code,
(case when ms.is_valid = 1 then '启用' else '禁用' end) as isValidName,
(case when ms.scope = 'all' then '全店' else '单品' end) as scopeName,
ms.classify, ms.name, ms.type, ms.date_created, ms.scope, ms.is_valid,
pc.name as classifyName
from mkt_showcase ms
left join purchase_classify pc on ms.classify = pc.id
where 1=1 and ms.del_flag = "F"
<#if name?? && name?length gt 0>
    and ms.name like '%base-service-biz%'
</#if>
<#if (classify??) && (classify != '0')>
    and ms.classify = ${classify}
</#if>
<#if type?? && type?length gt 0>
    and ms.type = 'base-service-biz'
</#if>
<#if isValid?? && isValid?length gt 0>
    and ms.is_valid = '${isValid}'
</#if>
order by date_created desc limit ?, ?