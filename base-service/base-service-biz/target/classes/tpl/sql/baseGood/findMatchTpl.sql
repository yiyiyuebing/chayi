select t.*
from good_page_tpl t
join good_page_tpl_apply ta on t.id = ta.tpl_id
where t.tpl_class_code = "${query.pageTplClassify}"
and t.order_biz_type = "${query.orderBizType}"
<#if query.goodSkuIdList?? && query.goodSkuIdList?size gt 0>
    and (
    <#list query.goodSkuIdList as goodSkuId>
        <#if goodSkuId_index gt 0>
            or
        </#if>
            find_in_set("${goodSkuId}", ta.good_ids)
    </#list>
    )
    and ta.apply_scope = "good"
<#elseif query.classifyIdList?? && query.classifyIdList?size gt 0>
    and (
    <#list query.classifyIdList as classifyId>
        <#if classifyId_index gt 0>
            or
        </#if>
            find_in_set("${classifyId}", ta.classify_ids)
    </#list>
    )
    and ta.apply_scope = "classify"
<#else>
    and ta.apply_scope = "all"
</#if>
and t.is_valid = "T"
and t.del_flag = "F"
and ta.is_valid = "T"
and ta.del_flag = "F"
order by ta.sort asc, t.last_updated desc