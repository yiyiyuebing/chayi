SELECT
	ifnull(round(sum(payment_amount), 2), 0) tomorowAmtTotal
FROM
	indent
WHERE
	subbranch_id in (${shipIds})  
AND STATUS <> 11
AND STATUS <> 1
AND date_format(pay_time , '%Y-%m-%d') = date_format(?, '%Y-%m-%d')