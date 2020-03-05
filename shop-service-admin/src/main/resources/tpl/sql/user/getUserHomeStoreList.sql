SELECT
	ss.id,
	ss.`name` as homeStoreName,
	(
		SELECT
			i.pay_time
		FROM
			indent i
		WHERE
			i.buyer_id = wui.ID
		AND i.subbranch_id = ss.id
		LIMIT 0,
		1
	) as buyTime
FROM
	weixin_user_info wui
LEFT JOIN weixin_store_weixinuser wsw ON wui.id = wsw.weixinuser_id
LEFT JOIN store_subbranch ss ON ss.id = wsw.store_id
WHERE
	wui.id = ?