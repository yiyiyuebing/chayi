<#if orderBizType ?? && orderBizType =="purchase">
  select c.pc_album_id from sp_presell_good spg
  LEFT JOIN purchase_goods pg on pg.id = spg.good_id
  LEFT JOIN cargo c on pg.cargo_id = c.id
  where spg.activity_id = ?
  order by pg.order_index desc,pg.sale_num desc LIMIT 0,1
<#else>
  select c.pc_album_id from sp_presell_good spg
  LEFT JOIN trade_good tg on tg.id = spg.good_id
  LEFT JOIN cargo c on tg.cargo_id = c.id
  where spg.activity_id = ?
  order by tg.sort desc,tg.sale_num desc LIMIT 0,1
</#if>