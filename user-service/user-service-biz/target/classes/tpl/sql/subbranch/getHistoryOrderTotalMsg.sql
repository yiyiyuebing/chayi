select
	ifnull(count(*), 0) historyNumTotal ,
	ifnull(round(sum(payment_amount), 2), 0.00) hostoryAmtTotal
from
	indent
where
	subbranch_id in (${shipIds})
and status <> 11
and status <> 1
and status <> 8
and status <> 9