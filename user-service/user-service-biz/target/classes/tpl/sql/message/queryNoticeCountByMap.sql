select count(1) as noticeCount from message m
	where m.type = 0
	<#if status??>
		and m.status = ${status}
	</#if>
	<#if clientId??>
		and m.client_id = ${clientId}
	</#if>
	<#if storeId??>
		and m.store_id = ${storeId}
	</#if>
	and m.id in (select mc1.message_id from message_content mc1
	where mc1.status = 0 and mc1.id in (select max(mc2.id) from message_content mc2 GROUP BY mc2.message_id))