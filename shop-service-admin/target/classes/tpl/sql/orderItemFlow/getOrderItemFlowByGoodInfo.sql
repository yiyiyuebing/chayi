 select * from order_item_as_flow where 1 = 1
 <#if goodSkuId?? && goodSkuId != ''>
  and good_sku_id = ${goodSkuId}
 </#if>
 <#if orderId?? && orderId != ''>
  and order_id = ${orderId}
 </#if>
 <#if orderType?? && orderType != ''>
  and order_type = '${orderType}'
 </#if>
 order by date_created desc limit 0, 1;