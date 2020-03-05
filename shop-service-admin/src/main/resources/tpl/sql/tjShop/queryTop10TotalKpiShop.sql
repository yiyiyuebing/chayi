<#if purchseOrTrade == 'trade'>
select
s.number AS shopId,
s.department_num AS deptCode,
s.head_img_url AS headImgUrl,
COUNT(i.id) AS saleNum,
vsr.concat_name AS custumName
from indent i
LEFT JOIN trade_order_list_payment tolp ON tolp.order_id=i.id
LEFT JOIN (SELECT
	store.ssid,
	ss.*
FROM
	store_subbranch ss
LEFT JOIN (
		SELECT
			sss.id

 AS ssid,
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
LEFT JOIN vtwo_store_role vsr ON vsr.store_id = s.id
WHERE i.`status` !=20
AND i.`status` != 1
AND tolp.pay_status='T'
GROUP BY s.id ORDER BY saleNum DESC  LIMIT 0,9
 </#if>

 <#if purchseOrTrade == 'purchase'>
SELECT
ssh.head_img_url AS headImgUrl,
ssh.number AS shopId,
ssh.department_num AS deptCode,
vsr.concat_name AS custumName,
COUNT(por.id) AS saleNum
FROM
store_subbranch ssh
LEFT JOIN purchase_order por ON por.buyer_id=ssh.id
LEFT JOIN purchase_order_list_payment polp ON polp.order_id = por.id
LEFT JOIN vtwo_store_role vsr ON vsr.store_id = ssh.id
WHERE por.`status` != 20
AND por.`status` !=1
AND polp.pay_status = 'T'
GROUP BY ssh.id ORDER BY saleNum DESC LIMIT 0,9
 </#if>