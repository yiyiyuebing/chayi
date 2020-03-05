select i.*,
s.number AS storeNum,
s.name as storeName,
s.id as subbranch_id,
s.sub_account_flag as is_sub_account,
s.department_num as storeDeptNum,
tope.presell_type as presllType,
tope.presell_amount as presellAmount,
tope.presell_end as presellEnd,
tope.presell_first as presellFirst,
sd.value AS expressMethod,
tolp.id as weixinTraderId,
spa.ship_time AS shipTime
from indent i
	left join trade_order_presell_extra tope on tope.order_id = i.id
	LEFT JOIN sp_presell_activity spa ON spa.id = tope.presell_activity_id
	LEFT JOIN trade_order_list_payment tolp ON tolp.order_id = i.id
  LEFT JOIN sys_dict sd ON i.express_id = sd.code
	LEFT JOIN (SELECT
	store.ssid,
	store.sub_account_flag,
	ss.*
FROM
	store_subbranch ss
LEFT JOIN (
		SELECT
			sss.id AS ssid,
			sss.is_sub_account as sub_account_flag,
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
where i.id = ?
GROUP BY i.id;