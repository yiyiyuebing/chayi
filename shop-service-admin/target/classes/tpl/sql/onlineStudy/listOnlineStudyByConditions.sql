select
ID, title, study_type as studyType, is_share as isShare, label, material,
(select t.name from event_online_study_type t where t.id=study_type) as studyTypeName, study_child_type as studyChildType,
type, read_num as readNum, author, video_url as videoUrl, label, study_tag as studyTag, file, cove_pic as covePic,
(select i.pic_url from Image i where i.id=cove_pic) as covePicUrl, create_time as createTime, date_format(create_time, '%Y-%c-%d') as createTime1,
create_by as createBy, update_time as updateTime, update_by as updateBy, study_tag as studyTag, content
from event_online_study  where 1=1 AND video_show=0
<#if title?? && label??>
    AND (title like '%${title}%' OR label like '%${label}%' )
</#if>
<#if studyType??>
    and study_type = ${studyType}
</#if>
<#if studyChildType??>
    and (study_child_type like '%${studyChildType}%' or study_child_type = 0)
</#if>
<#if type?? && type != 0>
    and type = ${type}
</#if>
order by ${order} ${orderType} limit ?, ?;