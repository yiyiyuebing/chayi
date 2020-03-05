select
  i.id as id, i.order_no as orderNo, i.pay_time as payTime, ifnull(i.carriage, 0.00) as carriage, ifnull(i.buyer_carriage, 0.00) as buyerCarriage,
  i.pay_type as payType, i.number as number, i.buyer_id as buyerId, i.status as status,
  ifnull(i.payment_amount, 0.00) as paymentAmount, i.remark_level as remarkLevel, i.remark as remark, i.ship_notice as shipNotice,
  i.total_amount as totalAmount, i.receiver as receiver, i.receiver_phone as receiverPhone,
  i.province, i.city, i.district, i.address, i.express_number as expressNumber, ifnull(i.final_amount, 0.00) as finalAmount,
  i.express_company as expressCompany, i.type, i.invoice_name, i.invoice_content, i.invoice_duty_paragraph,
  i.order_type as orderType,
  s.is_sub_account as isSubAccount, i.create_time as createTime, i.ship_time as shipTime, i.finish_time as finishTime, i.evaluate_time as evaluateTime,
  r.role_id as roleId,
  b.user_name as buyerName,
  r.concat_name as concatName,
  s.id as subbranchId,
  b.department_num as storeDeptNum,
  b.number as storeNum,
  b.name as storeName,
  s.user_name as storeUserName,
  i.order_client_type
from
  purchase_order i
  LEFT JOIN vtwo_store_role r ON i.buyer_id = r.store_id
  LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
  LEFT JOIN store_subbranch b ON b.id=i.buyer_id
  left join purchase_order_list il on il.order_id = i.id
  left join cargo_sku cs on il.cargo_sku_id = cs.id
where 1=1  and i.status != 0
    <#if concatName?? && concatName?length gt 0> and r.concat_name like concat('%', '${concatName ! ''}', '%') </#if>
    <#if orderClientType?? && orderClientType?length gt 0> and i.order_client_type like concat('%', '${orderClientType ! ''}', '%') </#if>
      <#if cargoNo?? && cargoNo?length gt 0> and cs.code like concat('%', '${cargoNo ! ''}', '%') </#if>
      <#if goodName?? && goodName?length gt 0> and il.pur_goods_name like concat('%', '${goodName ! ''}', '%') </#if>
      <#if orderNo?? && orderNo?length gt 0> and i.order_no like concat('%', '${orderNo ! ''}', '%') </#if>
      <#if buyerName?? && buyerName?length gt 0> and b.user_name like concat('%', '${buyerName ! ''}', '%') </#if>
      <#if storeName?? && storeName?length gt 0> and s.name like concat('%', '${storeName ! ''}', '%') </#if>
      <#if storeNum?? && storeNum?length gt 0> and s.number = ${storeNum}</#if>
      <#if storeDeptNum?? && storeDeptNum?length gt 0> and s.department_num = ${storeDeptNum} </#if>
      <#if receiver?? && receiver?length gt 0> and i.receiver like concat('%', '${receiver ! ''}', '%') </#if>
      <#if receiverPhone?? && receiverPhone?length gt 0> and i.receiver_phone like concat('%', '${receiverPhone ! ''}', '%') </#if>
      <#if indentStatusStr?? && indentStatusStr?length gt 0> and i.status in (${indentStatusStr}) </#if>
      <#if startTimeStr?? && startTimeStr?length gt 0> and i.create_time >= '${startTimeStr}' </#if>
      <#if endTimeStr?? && endTimeStr?length gt 0>  and i.create_time <= '${endTimeStr}' </#if>
      <#if type?? && type?length gt 0> and i.type = '${type}' </#if>
      <#if dealStatus?? && dealStatus?length gt 0> and i.deal_status = ${dealStatus} </#if>
      <#if isRemarkInfo?? && isRemarkInfo?length gt 0> and i.remark_level is not null </#if>
      <#if afterSaleService?? && afterSaleService?length gt 0> and il.status in ('${afterSaleService}')  </#if>
      <#if preSellStatusStr?? && preSellStatusStr?length gt 0> and i.status = '${preSellStatusStr}' and i.order_type = '${orderType}' </#if>
      <#if province?? && province?length gt 0 && province != '---请选择---'> and s.province_name like concat('%', '${province ! ''}', '%')  </#if>
      <#if city?? && city?length gt 0 && city != '---请选择---'> and s.city_name like concat('%', '${city ! ''}', '%')  </#if>
      <#if country?? && country?length gt 0 && country != '---请选择---'> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
      <#if needInvoice?? && needInvoice?length gt 0> and i.need_invoice = ${needInvoice} </#if>
group by i.id order by i.create_time desc limit ?, ?;
