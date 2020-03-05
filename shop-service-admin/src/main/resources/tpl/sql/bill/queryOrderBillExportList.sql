select
obr.order_no as indentName,
obr.settlement_date as settlementTime,
obr.time_cycle as cycle,
obr.pay_amount as paymentAmount,
obr.settlement_amount as supplyPrice,
i.finish_time, ss.number as shopNumber,
ss.department_num as deptNum,
ss.name as shopName,
(case when obr.is_sub_account = 'T' then '子账号' else '主账号' end) as accountType,
vsr.concat_name as buyerName,
sbc.name,
sbc.bank_card as bankCard,
sbc.bank_name as bankName,
sbc.bank_address as bankAddress,
i.pay_time,
i.buyer_carriage as carriage,
obr.`status` as payStatus,
GROUP_CONCAT(il.trade_good_name) AS good_name,
i.number as number
from order_bill_record obr
left join store_subbranch ss on ss.id = obr.subbranch_id
left join vtwo_store_role vsr on vsr.store_id = obr.subbranch_id
left join store_bank_card sbc on sbc.connect_Id = ss.id
left join indent i on i.id = obr.order_id
LEFT JOIN indent_list il ON i.id = il.indent_id
where 1=1
<#if shopId?? && shopId?length gt 0 && shopId != '0'>
    and ss.id = ${shopId}
</#if>
<#if startTime?? && startTime?length gt 0>
    and i.pay_time >= ${startTime}
</#if>
<#if endTime?? && endTime?length gt 0>
    and i.pay_time <= ${endTime}
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
group by obr.id
order by obr.settlement_date desc