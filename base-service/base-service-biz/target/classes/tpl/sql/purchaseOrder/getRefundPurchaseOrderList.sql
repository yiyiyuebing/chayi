SELECT
	group_concat(id) AS id,
	order_id,
	pur_goods_sku_id,
	pur_goods_id,
	STATUS,
	sum(number) AS number,
	sum(final_amount) AS final_amount,
	pur_goods_name,
	pur_goods_img_url,
	pur_goods_amount AS pur_goods_amount,
	pur_goods_type,
	ROUND(supply_price / number, 2) AS supply_price,
	create_time,
	is_sample,
	sum(sum_amount) AS sum_amount,
	cargo_sku_id,
	sum(original_amount) AS original_amount,
	sum(discount_amount) AS discount_amount,
	return_time,
	last_updated,
	gift_flag,
	sum(return_amount) AS return_amount,
	ship_cancel_after,
	receive_cancel_after,
	order_no,
	order_type
FROM
	(
		SELECT
			pol.*,
			po.order_no,
			po.order_type
		FROM
			purchase_order_list pol
		JOIN order_item_as_flow oiaf ON pol.order_id = oiaf.order_id
		JOIN purchase_order po ON po.id = pol.order_id
		AND pol.pur_goods_sku_id = oiaf.good_sku_id
		WHERE
			oiaf.as_type != "exchange"
		AND oiaf.order_type = "purchase"
 		AND po.buyer_id = "${query.userId}"
		AND oiaf.flow_status != "cancel_refund"
		AND oiaf.flow_status != "cancel_exchange"
		AND oiaf.flow_status != "cancel_return"
 		<#if query.flowAsType?? && query.flowAsType != "">
 			AND oiaf.as_type = "${query.flowAsType}"
 		</#if>
 		<#if query.flowStatusList?? && query.flowStatusList?size gt 0>
 			AND oiaf.flow_status IN (
 			<#list query.flowStatusList as flowStatus>
 				<#if flowStatus_index gt 0>
 					,
 				</#if>
 				"${flowStatus}"
 			</#list>
 			)
 		</#if>
 		<#if query.startDate?? && query.startDate != "">
 			AND oiaf.start_time >= "${query.startDate}"
 		</#if>
 		<#if query.endDate?? && query.endDate != "">
 			AND oiaf.end_time <= "${query.endDate}"
 		</#if>
		ORDER BY
			oiaf.start_time DESC
	) t
GROUP BY
	order_id,
	pur_goods_sku_id
LIMIT ${((query.pageNo - 1) * query.pageSize)?c},${query.pageSize}