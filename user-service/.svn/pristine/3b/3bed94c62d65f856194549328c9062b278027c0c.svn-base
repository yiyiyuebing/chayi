<#if queryType?? && queryType == 'totalNum'>
select count(*) as totalNum from indent i
where i.buyer_id = ${fansId}
and i.subbranch_id = ${shopId};
</#if>
<#if queryType?? && queryType == 'recentBuyDate'>
select max(i.pay_time) as recentBuyDate from indent i
where i.buyer_id = ${fansId}
and i.subbranch_id = ${shopId};
</#if>
<#if queryType?? && queryType == 'dealTotalAmount'>
select ifnull(sum(i.final_amount), 0.00) as dealTotalAmount from indent i
where
1=1 and i.status = 13
and i.buyer_id = ${fansId}
and i.subbranch_id = ${shopId};
</#if>
<#if queryType?? && queryType == 'dealNum'>
select count(*) as dealNum from indent i
where
1=1 and i.status = 13
and i.buyer_id = ${fansId}
and i.subbranch_id = ${shopId};
</#if>
<#if queryType?? && queryType == 'buyNum'>
select ifnull(sum(il.number), 0) as buyNum from indent i
left join indent_list il on il.indent_id = i.id
where
1=1 and i.status = 13
and i.buyer_id = ${fansId}
and i.subbranch_id = ${shopId};
</#if>