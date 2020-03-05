select
 i.id, i.name, i.receiver, i.receiver_phone as receiverPhone, concat(i.province, i.city, i.town, i.address) as address,
 i.invoice_content as invoiceContent, i.invoice_name as invoiceName,  i.buyer_carriage as buyerCarriage,
 i.payment_amount as paymentAmount, i.buyer_remark as buyerRemark, i.buy_type as buyType,
 i.status, i.create_time as createTime, i.pay_time as payTime, i.ship_time as shipTime, i.type, i.indent_source as indentSource,
 i.stage_order as stageOrder, i.pre_amount as preAmount, i.earnest_amount as earnestAmount, i.last_amount as lastAmount, i.last_earnest_amount as last_earnest_amount,
 i.express_company as expressCompany, i.express_number as expressNumber, i.pay_type as payType, i.pay_account as payAccount, i.remark_info as remarkInfo,
 wui.nickname as buyerName,
 (SELECT id FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS subbranchId,
(select ss.department_num from store_subbranch ss where ss.id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) as storeDeptNum,
 (select ss.number from store_subbranch ss where ss.id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) as storeNum,
 (select ss.name from store_subbranch ss where ss.id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) as storeName,
 (select ss.is_sub_account from store_subbranch ss where ss.id = i.subbranch_id) as isSubAccount,
(select ss.user_name from store_subbranch ss where ss.id = i.subbranch_id and ss.is_sub_account = 'T') as userName,
	(select ss.mobile from store_subbranch ss where ss.id = i.subbranch_id and ss.is_sub_account = 'T') as mobile,
(select sd.value from sys_dict sd where sd.code = i.express_id) as postMethod
from
indent i
LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
left join indent_list il on il.indent_id = i.id
left join cargo_sku cs on il.cargo_sku_id = cs.id
left join weixin_user_info wui on i.buyer_id = wui.id
where 1=1
      <#if ids??>
         and i.id in (${ids})
      <#else>
		 <#if goodSku?? && goodSku?length gt 0> and cs.code like concat('%', '${goodSku ! ''}', '%') </#if>
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
		  <#if afterSaleService?? && afterSaleService?length gt 0> and il.status = '${afterSaleService}'  </#if>
		  <#if province?? && province?length gt 0> and s.province_name like concat('%', '${province ! ''}', '%')  </#if>
		  <#if city?? && city?length gt 0> and s.city_name like concat('%', '${city ! ''}', '%')  </#if>
		  <#if country?? && country?length gt 0> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
      </#if>
group by i.id order by i.create_time desc;
