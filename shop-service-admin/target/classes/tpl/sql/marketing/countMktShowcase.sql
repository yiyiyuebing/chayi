select count(*) from mkt_showcase ms
left join purchase_classify pc on ms.classify = pc.id
where 1=1 and ms.del_flag = "F"
<#if name?? && name?length gt 0>
    and ms.name like '%shop-service-admin%'
</#if>
<#if (classify??) && (classify != '0')>
    and ms.classify = ${classify}
</#if>
<#if type?? && type?length gt 0>
    and ms.type = 'shop-service-admin'
</#if>
<#if isValid?? && isValid?length gt 0>
    and ms.is_valid = '${isValid}'
</#if>