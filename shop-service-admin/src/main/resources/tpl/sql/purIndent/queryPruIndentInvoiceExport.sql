select
order_no, create_time, number, receiver_phone, receiver,
invoice_name, invoice_content, invoice_duty_paragraph, status as indentStatus
from purchase_order where need_invoice = 1
<#if startTimeStr??> and create_time >= '${startTimeStr}' </#if>
<#if endTimeStr??>  and create_time <= '${endTimeStr}' </#if>
<#if orderNo??> and order_no like concat('%', '${orderNo ! ''}', '%') </#if>
<#if receiver??> and receiver like concat('%', '${receiver ! ''}', '%') </#if>
;