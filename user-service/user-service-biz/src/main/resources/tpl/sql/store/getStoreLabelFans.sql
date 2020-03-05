select wusl.label_id,
wui.id,
ifnull(wsw.nickname, wui.nickname) as nickname,
ifnull(wsw.tel, wui.tel) as tel,
 ifnull(wsw.headImgUrl, wui.headImgUrl) as headImgUrl
from weixin_user_store_label wusl
left join weixin_store_weixinuser wsw on wusl.weixin_id = wsw.weixinuser_id
left join weixin_user_info wui on wui.ID = wsw.weixinuser_id
where 1=1 and wusl.label_id in (${labelIds}) and wusl.store_id = ${shopId}
group by wusl.label_id, wsw.weixinuser_id