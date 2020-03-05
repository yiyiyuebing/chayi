select wsw.*, ss.user_name as storeName
from weixin_store_weixinuser wsw
left join store_subbranch ss on ss.id = wsw.store_id
where wsw.weixinuser_id in (${fansIds})
and wsw.store_id in(${shopIds})
order by wsw.date_created asc
;
