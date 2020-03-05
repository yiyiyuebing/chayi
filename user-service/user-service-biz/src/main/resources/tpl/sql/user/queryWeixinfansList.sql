select
wui.ID as id
,IFNULL(wsw.nickname,wui.nickname) as nickName
,IFNULL(wsw.sex,wui.sex) as sex
,IFNULL(wsw.headImgUrl,wui.headImgUrl) as picUrl
,IFNULL(wsw.sex,wui.sex) as sex
,IFNULL(wsw.tel,wui.tel) as tel
,wsw.date_created
,sum(ifnull(il.final_amount, 0)) as orderAmount
,sum(ifnull(il.number, 0)) as orderNum
 from weixin_store_weixinuser wsw 
left join weixin_user_info wui on wui.ID = wsw.weixinuser_id
left join indent i on i.buyer_id = wui.ID and i.subbranch_id in (${shopIds})
left join indent_list il on il.indent_id = i.id
where wsw.store_id in(${shopIds}) and wui.ID is not null
<#if searchKeyword?? && searchKeyword?length gt 0>
 and (wsw.user_name like CONCAT('%', '${searchKeyword}', '%')
or wui.user_name like CONCAT('%', '${searchKeyword}', '%')
or wsw.tel like CONCAT('%', '${searchKeyword}', '%')
or wui.tel like CONCAT('%', '${searchKeyword}', '%')
or wsw.nickname like CONCAT('%', '${searchKeyword}', '%')
or wui.nickname like CONCAT('%', '${searchKeyword}', '%') )
</#if>
group by wui.ID
<#if orderBy?? && orderBy?length gt 0 && orderBy="subscribeTime">
order by wsw.date_created ${orderType}
<#elseif orderBy?? && orderBy?length gt 0 && orderBy="orderNum">
order by orderNum ${orderType}
<#elseif orderBy?? && orderBy?length gt 0 && orderBy="orderAmount">
order by orderAmount ${orderType}
<#else>
order by wsw.date_created desc
</#if>
