select
    s.id, s.name, s.user_name, s.mobile, s.level_id, s.state, s.password, s.head_img_url, s.email, s.weixin,
    s.description, s.number, s.create_by, s.create_time, s.store_id, s.update_time, s.province, s.city,
    s.country, s.address, s.phone, s.province_name, s.country_name, s.city_name, s.department_num,r.business_content,
    (select IFNULL(update_status, 0) FROM vtwo_store_info_back WHERE store_id = s.id ORDER BY create_time desc limit 1) update_status
    ,r.id_card_index,r.id_card_back,r.role_id, r.concat_name
    ,r.business_licence,r.tax_photo,r.status AS verify,r.reason
    from store_subbranch s LEFT JOIN vtwo_store_role r ON s.id = r.store_id
    where s.id = ?