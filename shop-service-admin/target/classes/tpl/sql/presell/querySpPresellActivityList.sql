SELECT
	spa.id,
	spa. NAME,
	spa.memo,
	spa.presell_type,
	spa.presell_start,
	spa.presell_end,
	spa.payment_start,
	spa.payment_end,
	spa.ship_time,
	spa.is_valid,
	s.num
FROM
	sp_presell_activity spa
LEFT JOIN (
	SELECT
		spg.activity_id,
		count(*) as num
	FROM
		sp_presell_good spg
	GROUP BY
		spg.activity_id
) s ON s.activity_id = spa.id
where spa.del_flag='F'
<#if name?? && name !="">
  and spa.name like "%shop-service-admin%"
</#if>
<#if presellType ?? && presellType !="">
	and spa.presell_type = "${presellType}"
</#if>
<#if status ?? && status =="underway">
  and	spa.presell_end >= sysdate() and spa.presell_start <= sysdate()
</#if>
<#if status ?? && status =="over">
	and spa.presell_end < sysdate()
</#if>
<#if status ?? && status =="NotStarted">
	and spa.presell_end >= sysdate() and  spa.presell_start > sysdate()
</#if>
<#if orderBizType ?? && orderBizType!="">
	and spa.order_biz_type = "${orderBizType}"
</#if>

