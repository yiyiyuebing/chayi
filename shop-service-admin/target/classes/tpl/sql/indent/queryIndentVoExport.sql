select
 i.id, i.name, i.receiver, i.receiver_phone as receiverPhone, concat(i.province, i.city, i.town, i.address) as address,
 i.invoice_content as invoiceContent, i.invoice_name as invoiceName,  i.invoice_duty_paragraph, i.buyer_carriage as buyerCarriage,
 i.payment_amount as paymentAmount, i.buyer_remark as buyerRemark, i.buy_type as buyType, i.order_type as orderType,
 i.status, i.create_time as createTime, i.pay_time as payTime, i.ship_time as shipTime, i.type, i.indent_source as indentSource, i.refund_amount as refundAmount,
 i.pre_amount as preAmount, i.earnest_amount as earnestAmount, i.last_amount as lastAmount, i.last_earnest_amount as last_earnest_amount,
 i.express_company as expressCompany, i.express_number as expressNumber, i.pay_type as payType, i.pay_account as payAccount, i.remark_info as remarkInfo,
 wui.nickname as buyerName,
 shopmain.id AS subbranchId,
 shopmain.department_num as storeDeptNum,
 shopmain.number as storeNum,
 shopmain.name as storeName,
 s.is_sub_account as isSubAccount,
 s.user_name as userName,
 s.mobile as mobile,
 shopmain.id as subbranchId,
 sd.value as postMethod,
 max(tolp_1.stage_num) as stageOrder,
 i.indent_shop_type
from
indent i
LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
LEFT JOIN (SELECT
	store.ssid,
	ss.*
FROM
	store_subbranch ss
LEFT JOIN (
		SELECT
			sss.id AS ssid,
			CASE
		WHEN sss.is_sub_account = 'T' THEN
			sss.parent_subranch_id
		ELSE
			sss.id
		END AS sid
		FROM
			store_subbranch sss
	) store ON store.sid = ss.id
) shopmain ON shopmain.ssid = i.subbranch_id
left join indent_list il on il.indent_id = i.id
left join cargo_sku cs on il.cargo_sku_id = cs.id
left join weixin_user_info wui on i.buyer_id = wui.id
left join sys_dict sd on sd.code = i.express_id
<#if concatName?? && concatName?length gt 0> LEFT JOIN vtwo_store_role r ON shopmain.ssid = r.store_id </#if>
LEFT JOIN trade_order_list_payment tolp on tolp.order_id = i.id
LEFT JOIN trade_order_list_payment tolp_1 on tolp_1.order_id = i.id
<#if orderType?? && orderType?length gt 0 && orderType == 'presell'>
	left join trade_order_presell_extra tope on tope.order_id = i.id
</#if>
where 1=1 and i.status != 0
      <#if ids??>
         and i.id in (${ids})
      <#else>
		 <#if goodSku?? && goodSku?length gt 0> and cs.code like concat('%', '${goodSku ! ''}', '%') </#if>
		 <#if indentShopType?? && indentShopType?length gt 0> and i.indent_shop_type like concat('%', '${indentShopType ! ''}', '%') </#if>
		 <#if goodName?? && goodName?length gt 0> and il.trade_good_name like concat('%', '${goodName ! ''}', '%') </#if>
		 <#if buyerName?? && buyerName?length gt 0> and wui.nickname like concat('%', '${buyerName ! ''}', '%') </#if>
		 <#if orderNo?? && orderNo?length gt 0> and i.name like concat('%', '${orderNo ! ''}', '%') </#if>
		 <#if storeName?? && storeName?length gt 0> and s.name like concat('%', '${storeName ! ''}', '%') </#if>
		  <#if storeNum?? && storeNum?length gt 0> and s.number = ${storeNum} </#if>
		  <#if storeDeptNum?? && storeDeptNum?length gt 0> and s.department_num = ${storeDeptNum} </#if>
		 <#if receiver?? && receiver?length gt 0> and i.receiver like concat('%', '${receiver ! ''}', '%') </#if>
		 <#if receiverPhone?? && receiverPhone?length gt 0> and i.receiver_phone like concat('%', '${receiverPhone ! ''}', '%') </#if>
		  <#if indentStatusStr?? && indentStatusStr?length gt 0>and i.status in (${indentStatusStr}) </#if>
		  <#if buyType?? && buyType?length gt 0> and i.buy_type like '${buyType}'</#if>
		  <#if startTimeStr?? && startTimeStr?length gt 0> and i.create_time >= '${startTimeStr}' </#if>
		  <#if endTimeStr?? && endTimeStr?length gt 0>  and i.create_time <= '${endTimeStr}' </#if>
		  <#if type?? && type?length gt 0> and i.type = '${type}' </#if>
		  <#if dealStatus?? && dealStatus?length gt 0> and i.deal_status = ${dealStatus} </#if>
		  <#if isRemarkInfo?? && isRemarkInfo?length gt 0> and i.remark_level is not null </#if>
		  <#if afterSaleService?? && afterSaleService?length gt 0> and il.status in ('${afterSaleService}')  </#if>
		  <#if preSellStatusStr?? && preSellStatusStr?length gt 0> and i.status = '${preSellStatusStr}' and i.order_type = '${orderType}' </#if>
		  <#if orderType?? && orderType?length gt 0 && orderType == 'presell'>
			and i.order_type = '${orderType}'
			<#if presellStatus?? && presellStatus?length gt 0>
			  <#if presellStatus == '1'>
				and tope.presell_type = 'second'
				and tolp.pay_status = 'F'
				and tolp.stage_num = 1
			  </#if>
			  <#if presellStatus == '2'>
				and tope.presell_type = 'second'
				and tolp.pay_status = 'F'
				and tolp.stage_num = 2
				and i.status = 20
			  </#if>
			  <#if presellStatus == '3'>
				and tope.presell_type = 'second'
				and tolp.pay_status = 'F'
				and tolp.stage_num = 2
				and i.status = 11
			  </#if>
			  <#if presellStatus == '4'>
				and tope.presell_type = 'second'
				and tolp.pay_status = 'T'
				and tolp.stage_num = 2
			  </#if>
			  <#if presellStatus == '5'>
				and tope.presell_type = 'one'
				and tolp.pay_status = 'F'
				and tolp.stage_num = 1
			  </#if>
			  <#if presellStatus == '6'>
				and tope.presell_type = 'one'
				and tolp.pay_status = 'T'
				and tolp.stage_num = 1
			  </#if>
			</#if>
		  </#if>
		  <#if province?? && province?length gt 0> and s.province_name like concat('%', '${province ! ''}', '%')  </#if>
		  <#if city?? && city?length gt 0> and s.city_name like concat('%', '${city ! ''}', '%')  </#if>
		  <#if country?? && country?length gt 0> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
      </#if>
group by i.id order by i.create_time desc limit 0, 10000;
