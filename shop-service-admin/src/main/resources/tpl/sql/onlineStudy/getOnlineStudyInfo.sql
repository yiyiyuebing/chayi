SELECT e.ID, e.title, e.study_type, e.study_tag, e.label, ifnull(e.is_share, 0) is_share,
( SELECT t. NAME FROM event_online_study_type t WHERE t.id = e.study_type ) AS study_type_name,
e.study_child_type, ( SELECT t. NAME FROM event_online_study_type t WHERE t.id = e.study_child_type ) AS study_child_type_name,
e.`type`, e.read_num, e.author, e.video_url, e.label, e.file, e.cove_pic, ( SELECT i.pic_url FROM Image i WHERE i.id = cove_pic ) AS cove_pic_url,
e.create_time, date_format(e.create_time, '%Y-%c-%d') AS createTime1, e.create_by, e.update_time, e.update_by, e.content,e.material,
IFNULL( ( SELECT is_like FROM vtwo_study_like WHERE study_id = e.id AND user_id = ? ), 0 ) is_like,
IFNULL( ( SELECT is_zan FROM vtwo_study_like WHERE study_id = e.id AND user_id = ? ), 0 ) is_zan
FROM event_online_study e WHERE 1 = 1 AND e.id = ?;