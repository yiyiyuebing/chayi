SELECT
	ifnull(count(*), 0) todayNumTotal
FROM
	indent
WHERE
	subbranch_id in (${shipIds}) 
and status <> 11
and STATUS <> 1
AND date_format(pay_time , '%Y-%m-%d') = date_format(${r"?"}, '%Y-%m-%d')