SELECT
	*
FROM
	sp_manzeng_activity_apply
WHERE
is_valid = "T"
AND del_flag = "F"
AND (
<#if query.goodSkuIdList?? && query.goodSkuIdList?size gt 0>
    ((
        <#list query.goodSkuIdList as goodSkuId>
            <#if goodSkuId_index gt 0>
                or
            </#if>
                find_in_set("${goodSkuId}", good_ids)
        </#list>
        )
        and apply_scope = "good"
    )
</#if>
<#if query.classifyIdList?? && query.classifyIdList?size gt 0>
    or ((
        <#list query.classifyIdList as classifyId>
            <#if classifyId_index gt 0>
                or
            </#if>
                find_in_set("${classifyId}", classify_ids)
        </#list>
        )
        and apply_scope = "classify"
    )
</#if>
    OR apply_scope = "all"
)