select obr.id, obr.order_no as indentName,
obr.settlement_date as settlementTime,
obr.time_cycle as cycle,
obr.status,
obr.pay_amount as paymentAmount,
obr.settlement_amount as supplyPrice,
i.finish_time, ss.number as shopNumber,
ss.department_num as deptNum, ss.name as shopName,
vsr.concat_name as buyerName,
obr.catch_amount AS catchAmount,
sl.`name` AS shopLevel
from
order_bill_record obr 
left join store_subbranch ss on ss.id = obr.subbranch_id
left join vtwo_store_role vsr on vsr.store_id = obr.subbranch_id
left join indent i on i.id = obr.order_id
LEFT JOIN store_level sl ON ss.level_id=sl.level_Id
where 1=1
<#if shopId?? && shopId?length gt 0 && shopId != '0'>
    and ss.`name`  LIKE  CONCAT('%', '${shopId}' ,'%')
</#if>
<#if startTime?? && startTime?length gt 0>
    and i.pay_time >= '${startTime}'
</#if>
<#if endTime?? && endTime?length gt 0>
    and i.pay_time <= '${endTime}'
</#if>
<#if orderId?? && orderId?length gt 0>
    and i.name  like  CONCAT('%', '${orderId}' ,'%')
</#if>
<#if status?? && status?length gt 0 &&  status != 0>
    AND NOW() > date_add(i.finish_time, interval ${cycle} day) AND obr.status = ${status}
</#if>
<#if status?? && status?length gt 0 && status == 0>
    AND NOW() < date_add(i.finish_time, interval ${cycle} day) AND obr.status = 1
</#if>
<#if deptNum?? && deptNum?length gt 0>
   AND ss.department_num LIKE  CONCAT('%', '${deptNum}' ,'%')
</#if>
<#if shopNum?? && shopNum?length gt 0>
   AND ss.number LIKE  CONCAT('%', '${shopNum}' ,'%')
</#if>
<#if buyerName?? && buyerName?length gt 0>
   AND vsr.concat_name LIKE  CONCAT('%', '${buyerName}' ,'%')
</#if>
<#if levelId?? && levelId?length gt 0>
  AND sl.level_Id = '${levelId}'
</#if>

 order by obr.settlement_date desc