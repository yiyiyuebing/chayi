select i.id, i.`name`,i.subbranch_id as shop_id,i.total_amount,i.payment_amount,i.create_time,i.`number` as count, express_number, express_company,
        i.type,i.receiver,i.status,i.carriage,i.buyer_carriage,b.status AS indent_status,f.flow_status,i.deal_status
		from
		indent i LEFT JOIN indent_bill b ON i.id=b.indent_id
		<#if status?? && status == "3">
		<#else>
		LEFT
		</#if>
		JOIN (
		select order_id,case flow_status when "success_refund" then null when "success_return" then null when "success_exchange" then null else flow_status end as flow_status
		    from order_item_as_flow
		    where flow_status != "cancel_refund"
		    AND flow_status != "cancel_exchange"
		    AND flow_status != "cancel_return"
		    AND flow_status != "refuse_refund"
		    AND flow_status != "refuse_exchange"
		    AND flow_status != "refuse_return") f ON i.id = f.order_id
        where i.subbranch_id in (${shopIds})
        <#if buyerId??>
        and i.buyer_id = ${buyerId}
        </#if>
        <#if status?? && status != "3">
        and i.status = ${status}
            <#if status == "2" || status == "5">
                 and i.deal_status is null
            </#if>
        </#if>
         group by i.id
        order by i.create_time desc limit ?,?