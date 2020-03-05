select count(1) from (select
count(*)
from
  purchase_order i
  <#if concatName?? && concatName?length gt 0> LEFT JOIN vtwo_store_role r ON i.buyer_id= r.store_id </#if>
  LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
  LEFT JOIN store_subbranch b ON b.id=i.buyer_id
  left join purchase_order_list il on il.order_id = i.id
  left join cargo_sku cs on il.material_sku_id = cs.id
  <#if orderType?? && orderType?length gt 0 && orderType == 'presell'>
    left join purchase_order_list_payment polp on polp.order_id = i.id
	left join purchase_order_presell_extra pope on pope.order_id = i.id
  </#if>
where 1=1  and i.status != 0
      <#if cargoNo?? && cargoNo?length gt 0> and cs.code like concat('%', '${cargoNo ! ''}', '%') </#if>
      <#if orderClientType?? && orderClientType?length gt 0> and i.order_client_type like concat('%', '${orderClientType ! ''}', '%') </#if>
      <#if goodName?? && goodName?length gt 0> and il.pur_goods_name like concat('%', '${goodName ! ''}', '%') </#if>
      <#if orderNo?? && orderNo?length gt 0> and i.order_no like concat('%', '${orderNo ! ''}', '%') </#if>
      <#if buyerName?? && buyerName?length gt 0> and b.user_name like concat('%', '${buyerName ! ''}', '%') </#if>
      <#if storeName?? && storeName?length gt 0> and s.name like concat('%', '${storeName ! ''}', '%') </#if>
      <#if storeNum?? && storeNum?length gt 0> and s.number = ${storeNum}</#if>
      <#if storeDeptNum?? && storeDeptNum?length gt 0> and s.department_num = ${storeDeptNum} </#if>
      <#if receiver?? && receiver?length gt 0> and i.receiver like concat('%', '${receiver ! ''}', '%') </#if>
      <#if receiverPhone?? && receiverPhone?length gt 0> and i.receiver_phone like concat('%', '${receiverPhone ! ''}', '%') </#if>
      <#if indentStatusStr?? && indentStatusStr?length gt 0>
        <#if indentStatusStr == '2' || indentStatusStr == '5' >
          and i.status in (${indentStatusStr})
          and i.deal_status is null
          <#elseif indentStatusStr == '11'>
          and (i.status in (${indentStatusStr}) or i.deal_status = 0  or i.deal_status = 1)
         <#else>
          and i.status in (${indentStatusStr})
         </#if>
       </#if>
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
            and pope.presell_type = 'second'
            and polp.pay_status = 'F'
            and polp.stage_num = 1
          </#if>
          <#if presellStatus == '2'>
            and pope.presell_type = 'second'
            and polp.pay_status = 'F'
            and polp.stage_num = 2
            and i.status = 20
          </#if>
          <#if presellStatus == '3'>
            and pope.presell_type = 'second'
            and polp.pay_status = 'F'
            and polp.stage_num = 2
            and i.status = 11
          </#if>
          <#if presellStatus == '4'>
            and pope.presell_type = 'second'
            and polp.pay_status = 'T'
            and polp.stage_num = 2
          </#if>
          <#if presellStatus == '5'>
            and pope.presell_type = 'one'
            and polp.pay_status = 'F'
            and polp.stage_num = 1
          </#if>
          <#if presellStatus == '6'>
            and pope.presell_type = 'one'
            and polp.pay_status = 'T'
            and polp.stage_num = 1
          </#if>
        </#if>
      </#if>
      <#if province?? && province?length gt 0 && province != '---请选择---'> and s.province_name like concat('%', '${province ! ''}', '%')  </#if>
      <#if city?? && city?length gt 0 && city != '---请选择---'> and s.city_name like concat('%', '${city ! ''}', '%')  </#if>
      <#if country?? && country?length gt 0 && country != '---请选择---'> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
      <#if needInvoice?? && needInvoice?length gt 0> and i.need_invoice = ${needInvoice} </#if>
group by i.id) a