select
 i.id, i.order_no, i.receiver, i.receiver_phone as receiverPhone, concat(i.address) as address,
 i.invoice_content as invoiceContent, i.invoice_name as invoiceName, i.invoice_duty_paragraph, ifnull(i.carriage, 0.00) as buyerCarriage,
 i.payment_amount as paymentAmount, i.buyer_remark as buyerRemark,
 i.status, i.create_time as createTime, i.pay_time as payTime, i.ship_time as shipTime, ifnull(i.type, 1) as type,
 i.pre_amount as preAmount, i.earnest_amount as earnestAmount, ifnull(i.refund_amount, 0.00) as refundAmount,
 i.last_amount as lastAmount, i.last_earnest_amount as last_earnest_amount,
 i.express_company as expressCompany, i.express_number as expressNumber, i.pay_type as payType,
 i.pay_account as payAccount, i.remark as remarkInfo, i.order_type as orderType,
 b.user_name as buyerName,
 s.id as subbranchId,
 s.department_num as storeDeptNum,
 s.number as storeNum,
 s.name as storeName,
 s.user_name as userName,
 s.mobile as mobile,
 r.concat_name as concatName,
 sd.value as postMethod,
 max(polp.stage_num) as stageOrder,
 i.order_client_type
from
purchase_order i
LEFT JOIN store_subbranch s ON s.id=i.buyer_id
left join purchase_order_list il on il.order_id = i.id
left join cargo_sku cs on il.cargo_sku_id = cs.id
LEFT JOIN store_subbranch b on b.id = i.subbranch_id
LEFT JOIN sys_dict sd on sd.code = i.express_id
LEFT JOIN purchase_order_list_payment polp on polp.order_id = i.id
LEFT JOIN vtwo_store_role r ON i.buyer_id = r.store_id
where 1=1 and i.status != 0
      <#if goodSku??> and cs.code like concat('%', '${goodSku ! ''}', '%') </#if>
      <#if orderClientType?? && orderClientType?length gt 0> and i.order_client_type like concat('%', '${orderClientType ! ''}', '%') </#if>
      <#if goodName??> and il.pur_goods_name like concat('%', '${goodName ! ''}', '%') </#if>
      <#if orderNo??> and i.order_no like concat('%', '${orderNo ! ''}', '%') </#if>
      <#if buyerName??> and b.user_name like concat('%', '${buyerName ! ''}', '%') </#if>
      <#if storeName??> and s.name like concat('%', '${storeName ! ''}', '%') </#if>
      <#if storeNum??> and s.number = ${storeNum}</#if>
      <#if storeDeptNum??> and s.department_num = ${storeDeptNum} </#if>
      <#if receiver??> and i.receiver like concat('%', '${receiver ! ''}', '%') </#if>
      <#if receiverPhone??> and i.receiver_phone like concat('%', '${receiverPhone ! ''}', '%') </#if>
      <#if indentStatusStr??> and i.status in (${indentStatusStr}) </#if>
      <#if startTimeStr??> and i.create_time >= '${startTimeStr}' </#if>
      <#if endTimeStr??>  and i.create_time <= '${endTimeStr}' </#if>
      <#if type??> and i.type = '${type}' </#if>
      <#if dealStatus??> and i.deal_status = ${dealStatus} </#if>
      <#if isRemarkInfo??> and i.remark_level is not null </#if>
      <#if afterSaleService??> and il.status in ('${afterSaleService}')  </#if>
      <#if preSellStatusStr?? && preSellStatusStr?length gt 0 && orderType?? && orderType?length gt 0> and i.status = '${preSellStatusStr}' and i.order_type = '${orderType}' </#if>
      <#if province??> and s.province_name like concat('%', '${province ! ''}', '%')</#if>
      <#if city??> and s.city_name like concat('%', '${city ! ''}', '%') </#if>
      <#if country??> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
group by i.id order by i.create_time desc limit 0, 10000;