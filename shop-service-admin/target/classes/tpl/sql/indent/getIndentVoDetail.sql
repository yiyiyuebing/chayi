select i.*,
s.number AS storeNum,
s.name as storeName,
s.department_num as storeDeptNum,
tope.presell_type as presllType,
tope.presell_amount as presellAmount,
tope.presell_end as presellEnd,
tope.presell_first as presellFirst
from indent i
	left join trade_order_presell_extra tope on tope.order_id = i.id
  LEFT JOIN (SELECT
	store.ssid,
	ss.*
FROM
	store_subbranch ss
LEFT JOIN (
		SELECT
			sss.id AS ssid,
			CASE
		WHEN sss.is_sub_account = 'T' THEN
			sss.parent_subranch_id
		ELSE
			sss.id
		END AS sid
		FROM
			store_subbranch sss
	) store ON store.sid = ss.id
) s ON s.ssid = i.subbranch_id
where i.id = ?;