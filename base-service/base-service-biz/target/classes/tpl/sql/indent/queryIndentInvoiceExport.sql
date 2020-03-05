select
name as order_no, create_time, number, receiver_phone, receiver,
invoice_name, invoice_content, status as indentStatus
from indent where need_invoice = 1
<#if startTimeStr??> and create_time >= '${startTimeStr}' </#if>
<#if endTimeStr??>  and create_time <= '${endTimeStr}' </#if>
<#if orderNo??> and name like concat('%', '${orderNo ! ''}', '%') </#if>
<#if receiver??> and receiver like concat('%', '${receiver ! ''}', '%') </#if>
;