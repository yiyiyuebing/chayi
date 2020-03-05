SELECT
	s.id id,
	s.cargo_sku_id,
	s.out_shelves_no,
	s.on_sales_no,
	s.on_pay_no,
	s.on_send_no,
	s.curr_stock as currStock,
	s.remain_count,
	k.code CODE,
	'0' AS type
FROM
	cargo_sku_stock s,
	cargo_sku k,
	cargo c
WHERE
	s.cargo_sku_id = k.id
AND k.cargo_id = c.id
<#if cInvCodes?? && cInvCodes !=''>
    and k.code in (${cInvCodes})
</#if>
;