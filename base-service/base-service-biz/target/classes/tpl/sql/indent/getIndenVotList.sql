select
  i.id as id, i.name as name, i.pay_time as payTime, i.buyer_carriage as buyerCarriage,
  i.pay_type as payType, i.number as number, i.buyer_id as buyerId, i.status as status,
  i.payment_amount as paymentAmount, i.remark_level as remarkLevel, i.remark_info as remarkInfo,
  i.total_amount as totalAmount, i.receiver as receiver, i.receiver_phone as receiverPhone,
  i.province, i.city, i.town, i.address, i.express_number as expressNumber, i.express_company as expressCompany,
  i.type, i.buy_type as buyType, i.invoice_name, i.invoice_content,
  s.is_sub_account as isSubAccount, i.create_time as createTime, i.ship_time as shipTime, i.finish_time as finishTime, i.evaluate_time as evaluateTime,
  r.role_id as roleId,
  (select nickname from weixin_user_info where id = i.buyer_id) as buyerName,
  (SELECT number FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeNum,
  (SELECT id FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS subbranchId,
  (SELECT department_num FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeDeptNum,
  (SELECT name FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeName,
  (SELECT user_name FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeUserName
from
  indent i
  LEFT JOIN vtwo_store_role r ON i.subbranch_id= r.store_id
  LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
  left join indent_list il on il.indent_id = i.id
  left join cargo_sku cs on il.cargo_sku_id = cs.id
  left join weixin_user_info wui on i.buyer_id = wui.id
where 1=1 and i.status != 0
    <#if cargoNo?? && cargoNo?length gt 0> and cs.code like concat('%', '${cargoNo ! ''}', '%') </#if>
    <#if goodName?? && goodName?length gt 0> and il.trade_good_name like concat('%', '${goodName ! ''}', '%') </#if>
    <#if buyerName?? && buyerName?length gt 0> and wui.nickname like concat('%', '${buyerName ! ''}', '%') </#if>
    <#if orderNo?? && orderNo?length gt 0> and i.name like concat('%', '${orderNo ! ''}', '%') </#if>
    <#if storeName?? && storeName?length gt 0> and s.name like concat('%', '${storeName ! ''}', '%') </#if>
      <#if storeNum??> and s.number = ${storeNum}</#if>
      <#if storeDeptNum??> and s.department_num = ${storeDeptNum}</#if>
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
      <#if needInvoice?? && needInvoice?length gt 0> and i.need_invoice = ${needInvoice} </#if>
group by i.id order by i.create_time desc limit ?, ?;