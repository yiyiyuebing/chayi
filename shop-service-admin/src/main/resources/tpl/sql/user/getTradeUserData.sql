SELECT
	wui.ID,
	wui.nickname,
	wui.headImgUrl,
	wui.weixin_num,
	wuie.remark,
 	wuie.label,
	wui.sex,
	twu.total_tran_amount AS totalTranAmount,
	twu.total_tran_num AS totalTranNum,
	twu.avg_tran_amount as avgTranAmount,
	twu.total_buy_num as buyNum,
	twu.last_tran_time as lastTranDate
FROM
	weixin_user_info wui
LEFT JOIN weixin_user_info_ext wuie on wuie.weixin_user_id = wui.ID
LEFT JOIN tj_weixin_user twu ON wui.ID = twu.weixin_user_id
where wui.id = ?