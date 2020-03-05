select count(1) from (select
count(*)
from
indent i
  LEFT JOIN vtwo_store_role r ON i.subbranch_id= r.store_id
  LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
  left join indent_list il on il.indent_id = i.id
  left join cargo_sku cs on il.cargo_sku_id = cs.id
  left join weixin_user_info wui on i.buyer_id = wui.id
where 1=1  and i.status != 0
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
group by i.id) a