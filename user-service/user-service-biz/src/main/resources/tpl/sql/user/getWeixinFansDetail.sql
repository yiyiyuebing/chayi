select
wui.ID as id,
ifnull(wsw.birthday, wui.birthday) as birthday,
ifnull(wsw.nickname, wui.nickname) as nickname,
ifnull(wsw.sex, wui.sex) as sex,
ifnull(wsw.country, wui.country) as country,
ifnull(wsw.province, wui.province) as province,
ifnull(wsw.city, wui.city) as city,
ifnull(wsw.language, wui.language) as language,
ifnull(wsw.headImgUrl, wui.headImgUrl) as headImgUrl,
ifnull(wsw.user_name, wui.user_name) as user_name,
ifnull(wsw.tel, wui.tel) as tel,
ifnull(wsw.weixin_num, wui.weixin_num) as weixin_num,
ifnull(wsw.person_sign, wui.person_sign) as person_sign,
wsw.remark,
group_concat(sl.name) as label,
concat(gra.province_name, gra.city_name, gra.area_name, gra.detail_addr) as defaultAddr,
ifnull(wsw.address, concat(gra.province_name, gra.city_name, gra.area_name, gra.detail_addr)) as address
from weixin_store_weixinuser wsw
left join weixin_user_info wui on wui.ID = wsw.weixinuser_id
left join weixin_user_store_label wusl on wusl.store_id = wsw.store_id and wusl.weixin_id = wui.ID
left join store_label sl on sl.store_id = wsw.store_id and sl.id = wusl.label_id
left join good_receipt_addr gra on gra.user_id = wui.ID and gra.`status`= 1
where 1=1
and wsw.store_id = ${shopId} and wui.ID = ${fansId}

