select count(1) as count from event_online_study where 1=1 AND video_show=0
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