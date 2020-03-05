select
tss.id,
tss.department_num as departmentNum,
tss.number,
tss.head_img_url as headImgUrl,
tss.concat_name as concatName,
tss.son_num as sonNum,
tss.total_tran_amount,
tss.total_tran_num,
tss.total_buy_num as totalBuyNum,
tss.last_tran_time as lastTranTime,
tss.avg_tran_amount
from tj_store_subbranch tss

LEFT JOIN
	(select po.buyer_id from purchase_order po
	 where 1=1
		 <#if startDate?? && startDate!="">
			and po.pay_time >= '${startDate}'
		</#if>
		<#if endDate?? && endDate!="">
			and po.pay_time <= '${endDate}'
		</#if>
		 group by po.buyer_id)as poo on poo.buyer_id = tss.id
where 1 = 1

<#if levelId?? && levelId!="">
	and tss.level_id = '${levelId}'
</#if>

<#if dpName?? && dpName!="">
	and tss.`name` like '%${dpName}%'
</#if>

<#if number?? && number!="">
	and tss.number like '%${number}%'
</#if>

<#if tranAmountStr?? && tranAmountStr!="">
  and tss.total_tran_amount >= '${tranAmountStr}'
</#if>
<#if tranAmountEnd?? && tranAmountEnd!="">
  and tss.total_tran_amount <= '${tranAmountEnd}'
</#if>

<#if tranNumStr?? && tranNumStr!="">
  and tss.total_tran_num >= '${tranNumStr}'
</#if>
<#if tranNumEnd?? && tranNumEnd!="">
  and tss.total_tran_num <= '${tranNumEnd}'
</#if>

<#if lastTranDateStr?? && lastTranDateStr!="">
  and tss.last_tran_time >= '${lastTranDateStr}'
</#if>
<#if lastTranDateEnd?? && lastTranDateEnd!="">
  and tss.last_tran_time <= '${lastTranDateEnd}'
</#if>

<#if buyNumStr?? && buyNumStr!="">
  and tss.total_buy_num >= '${buyNumStr}'
</#if>
<#if buyNumEnd?? && buyNumEnd!="">
  and tss.total_buy_num <='${buyNumEnd}'
</#if>

<#if (startDate?? && startDate!="")||(endDate?? && endDate!="")>
	and poo.buyer_id = tss.id
</#if>

<#if province?? && province!="">
	and tss.receiving_area like '%${province}%'
</#if>

<#if labelName?? && labelName!="">
	and tss.label like '%${labelName}%'
</#if>

<#if concatName?? && concatName!="">
 	and tss.concat_name like '%${concatName}%'
</#if>

<#if buybackTime?? && buybackTime!="">
	and datediff(tss.last_tran_time,tss.second_last_tran_time) < '${buybackTime}'
</#if>