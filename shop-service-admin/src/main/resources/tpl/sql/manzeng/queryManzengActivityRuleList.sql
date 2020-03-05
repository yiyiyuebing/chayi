select * from sp_manzeng_rule smr
where 1 = 1
and smr.activity_id in (${activityIds}) and smr.del_flag != 'T' and smr.is_valid != 'F'
<#if zengFlag?? && zengFlag?length gt 0>
and smr.zeng = '${zengFlag}';
</#if>
order by smr.man asc