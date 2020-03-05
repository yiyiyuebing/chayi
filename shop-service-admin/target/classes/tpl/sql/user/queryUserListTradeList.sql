select
twu.id,
twu.nick_name,twu.headImgUrl,
twu.total_tran_amount,
twu.total_tran_num,
twu.total_buy_num as buyNum,
twu.last_tran_time as lastTranDate,
twu.avg_tran_amount
from tj_weixin_user twu
LEFT JOIN
	(select ii.buyer_id from indent ii
	 where 1=1
	<#if startDate?? && startDate!="">
		and ii.pay_time >= '${startDate}'
	</#if>
	<#if endDate?? && endDate!="">
		and ii.pay_time <= '${endDate}'
	</#if>
	 group by ii.buyer_id)as iii on iii.buyer_id = twu.id
where 1 = 1
<#if nickName?? && nickName!="">
  and twu.nick_name LIKE '%${nickName}%'
</#if>

<#if tranAmountStr??>
  and twu.total_tran_amount >= ${tranAmountStr}
</#if>
<#if tranAmountEnd??>
  and twu.total_tran_amount <= ${tranAmountEnd}
</#if>

<#if tranNumStr??>
  and twu.total_tran_num >= '${tranNumStr}'
</#if>
<#if tranNumEnd??>
  and twu.total_tran_num <= '${tranNumEnd}'
</#if>

<#if lastTranDateStr?? && lastTranDateStr!="">
  and twu.last_tran_time >= '${lastTranDateStr}'
</#if>
<#if lastTranDateEnd?? && lastTranDateEnd!="">
  and twu.last_tran_time <= '${lastTranDateEnd}'
</#if>

<#if buyNumStr??>
  and twu.total_buy_num >= '${buyNumStr}'
</#if>
<#if buyNumEnd??>
  and twu.total_buy_num <='${buyNumEnd}'
</#if>

<#if (startDate?? && startDate!="")||(endDate?? && endDate!="")>
	and iii.buyer_id = twu.id
</#if>

<#if province?? && province!="">
	and twu.receiving_area like '%${province}%'
</#if>

<#if labelName?? && labelName!="">
	and twu.label like '%${labelName}%'
</#if>

<#if buybackTime?? && buybackTime!=0>
	and datediff(twu.last_tran_time,twu.second_last_tran_time) < '${buybackTime}'
</#if>