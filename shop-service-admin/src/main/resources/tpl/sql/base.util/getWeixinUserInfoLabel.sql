SELECT
weixin_user_info_ext.id,
weixin_user_info_ext.weixin_user_id AS weixinUserId,
weixin_user_info_ext.remark,
weixin_user_info_ext.label,
weixin_user_info_ext.del_flag AS delFlag,
weixin_user_info_ext.is_valid AS isValid,
weixin_user_info_ext.date_created AS dateCreated,
weixin_user_info_ext.last_updated AS lastUpdated
FROM
weixin_user_info_ext WHERE weixin_user_info_ext.weixin_user_id="${weixinUserId}"