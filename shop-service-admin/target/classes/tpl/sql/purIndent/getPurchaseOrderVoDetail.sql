select po.*,
s.number AS storeNum,
s.name as storeName,
s.user_name as buyerName,
s.department_num as storeDeptNum,
r.concat_name as concatName,
pope.presell_type as presllType,
pope.presell_amount as presellAmount,
pope.presell_end as presellEnd,
pope.presell_first as presellFirst
from purchase_order po
left join store_subbranch s on s.id = po.buyer_id
left join purchase_order_presell_extra pope on pope.order_id = po.id
LEFT JOIN vtwo_store_role r ON po.buyer_id = r.store_id
where po.id = ?;