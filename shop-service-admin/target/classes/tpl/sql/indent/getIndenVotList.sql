select
  i.id as id, i.name as name, i.pay_time as payTime, ifnull(i.carriage, 0.00) as carriage, ifnull(i.buyer_carriage, 0.00) as buyerCarriage,
  i.pay_type as payType, i.number as number, i.buyer_id as buyerId, i.status as status, ifnull(i.final_amount, 0.00) as finalAmount,
  ifnull(i.payment_amount, 0.00) as paymentAmount, i.remark_level as remarkLevel, i.remark_info as remarkInfo,
  i.total_amount as totalAmount, i.receiver as receiver, i.receiver_phone as receiverPhone, IFNULL(i.order_type, 'normal') as orderType,
  i.province, i.city, i.town, i.address, i.express_number as expressNumber, i.express_company as expressCompany, i.ship_notice as shipNotice,
  i.type, i.buy_type as buyType, i.invoice_name, i.invoice_content, i.invoice_duty_paragraph,
  s.is_sub_account as isSubAccount, i.create_time as createTime, i.ship_time as shipTime, i.finish_time as finishTime, i.evaluate_time as evaluateTime,
	wui.nickname as buyerName,
	s.number  AS storeNum,s.id AS subbranchId,s.department_num AS storeDeptNum,s.name AS storeName,
	s.user_name AS storeUserName,
	i.indent_shop_type
from
  indent i
  <#if concatName?? && concatName?length gt 0> LEFT JOIN vtwo_store_role r ON i.subbranch_id= r.store_id </#if>
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
) s ON s.ssid=i.subbranch_id
  left join indent_list il on il.indent_id = i.id
  left join cargo_sku cs on il.cargo_sku_id = cs.id
  left join weixin_user_info wui on i.buyer_id = wui.id
  <#if orderType?? && orderType?length gt 0 && orderType == 'presell'>
    left join trade_order_list_payment tolp on tolp.order_id = i.id
    left join trade_order_presell_extra tope on tope.order_id = i.id
  </#if>
where 1=1 and i.status != 0
    <#if cargoNo?? && cargoNo?length gt 0> and cs.code like concat('%', '${cargoNo ! ''}', '%') </#if>
    <#if indentShopType?? && indentShopType?length gt 0> and i.indent_shop_type like concat('%', '${indentShopType ! ''}', '%') </#if>
    <#if goodName?? && goodName?length gt 0> and il.trade_good_name like concat('%', '${goodName ! ''}', '%') </#if>
    <#if buyerName?? && buyerName?length gt 0> and wui.nickname like concat('%', '${buyerName ! ''}', '%') </#if>
    <#if concatName?? && concatName?length gt 0> and r.concat_name like concat('%', '${concatName ! ''}', '%') </#if>
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
      <#if afterSaleService?? && afterSaleService?length gt 0> and il.status in ('${afterSaleService}')   </#if>

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

      <#if province?? && province?length gt 0 && province != '---请选择---'> and s.province_name like concat('%', '${province ! ''}', '%')  </#if>
      <#if city?? && city?length gt 0 && city != '---请选择---'> and s.city_name like concat('%', '${city ! ''}', '%')  </#if>
      <#if country?? && country?length gt 0 && country != '---请选择---'> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
      <#if needInvoice?? && needInvoice?length gt 0> and i.need_invoice = ${needInvoice} </#if>
group by i.id order by i.create_time desc limit ?, ?;