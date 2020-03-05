select po.*,
s.number AS storeNum,
s.name as storeName,
s.user_name as buyerName,
s.department_num as storeDeptNum,
r.concat_name as concatName,
pope.presell_type as presllType,
pope.presell_amount as presellAmount,
pope.presell_end as presellEnd,
pope.presell_first as presellFirst,
sd.value AS expressMethod,
spa.ship_time AS shipTime,
polp.id AS weixinTraderId
from purchase_order po
LEFT JOIN purchase_order_list_payment polp ON polp.order_id = po.id
left join store_subbranch s on s.id = po.buyer_id
left join purchase_order_presell_extra pope on pope.order_id = po.id
LEFT JOIN sp_presell_activity spa ON spa.id = pope.presell_activity_id
LEFT JOIN vtwo_store_role r ON po.buyer_id = r.store_id
LEFT JOIN sys_dict sd ON po.express_id = sd.code
WHERE po.id = ?
GROUP BY po.id;