select sl.*, 1 as isChecked from store_label sl
left join weixin_user_store_label susl on susl.label_id = sl.id
where susl.store_id in (${storeIds}) and susl.weixin_id = ${fansId};
