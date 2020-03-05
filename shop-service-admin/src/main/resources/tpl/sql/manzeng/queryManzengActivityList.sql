SELECT
	smay.*,
	smaa.apply_scope
FROM
	sp_manzeng_activity smay
	left join sp_manzeng_activity_apply smaa on smaa.activity_id = smay.id
WHERE
	1 = 1 and smay.del_flag != 'T'
<#if status ?? && status =="underway">
  and	smay.end_time >= sysdate() and smay.start_time <= sysdate()
</#if>
<#if status ?? && status =="over">
	and smay.end_time < sysdate()
</#if>
<#if status ?? && status =="NotStarted">
	and smay.end_time >= sysdate() and  smay.start_time > sysdate()
</#if>
<#if activityName ?? && activityName !="">
	and smay.name like '%${activityName}%'
</#if>

<#if goodSkuIdList?? && goodSkuIdList?size gt 0>
	and (
	<#list goodSkuIdList as goodSkuId>
		<#if goodSkuId_index gt 0>
				or
		</#if>
			find_in_set("${goodSkuId}", smaa.good_ids)
	</#list>
	)
</#if>

AND smay.order_biz_type = '${orderBizType}' GROUP BY smay.id order by smay.is_valid desc, smay.start_time desc, smay.date_created asc
