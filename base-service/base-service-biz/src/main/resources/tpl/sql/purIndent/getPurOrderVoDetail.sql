select i.*,
(SELECT number FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeNum,
(SELECT name FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeName,
(SELECT department_num FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS storeDeptNum,
b.user_name as buyerName
from
purchase_order i
left join store_subbranch b on b.id = i.buyer_id
where i.id=?