select od.*,
<#if orderType?? && orderType == 'trade'>
(case when od.oper_man_type != 'buyer' then 'http://o7o0uv2j1.bkt.clouddn.com/1501146974519.png' else ifnull(wi.headImgUrl, 'http://o7o0uv2j1.bkt.clouddn.com/1501146974519.png') end) as headImgUrl,
(case when od.oper_man_type != 'buyer' then sr.note else wi.nickname end) as operManName
</#if>
<#if orderType?? && orderType == 'purchase'>
(case when od.oper_man_type != 'buyer' then 'http://o7o0uv2j1.bkt.clouddn.com/1501146974519.png' else ifnull(s.head_img_url, 'http://o7o0uv2j1.bkt.clouddn.com/1501146974519.png') end) as headImgUrl,
(case when od.oper_man_type != 'buyer' then sr.note else ifnull(s.name, '') end) as operManName
</#if>
 from order_item_reply od
<#if orderType?? && orderType == 'trade'>
 left join weixin_user_info wi on od.oper_man = wi.ID
</#if>
<#if orderType?? && orderType == 'purchase'>
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
 ) s ON s.ssid=od.oper_man
</#if>
 left join staff_t st on st.STAFF_ID = od.oper_man
 left join _security_role_user sru on sru.user_id = od.oper_man
 left join _security_role sr on sr.id = sru.role_id
where 1=1
and od.oper_man_type != 'pending'
<#if orderNo?? && orderNo != ''>
and od.order_id = ${orderNo}
</#if>
<#if goodSkuId?? && goodSkuId != ''>
and od.good_sku_id = ${goodSkuId}
</#if>
<#if orderType?? && orderType != ''>
and od.order_type = '${orderType}'
</#if>
order by od.date_created desc;