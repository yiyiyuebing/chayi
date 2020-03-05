select od.*,
(case when od.oper_man_type != 'buyer' then './image/user2-160x160.jpg' else wi.headImgUrl end) as headImgUrl,
(case when od.oper_man_type != 'buyer' then st.STAFF_NAME else wi.nickname end) as operManName
 from order_item_reply od
left join weixin_user_info wi on od.oper_man = wi.ID
left join staff_t st on st.STAFF_ID = od.oper_man
where od.order_id = ? and od.good_sku_id = ? and od.order_type = ? order by od.date_created desc;