SELECT
	gpt.id,gpt.tpl_name,gptm.model_name,gpta.apply_scope,gpt.is_valid,gpta.sort,gpt.date_created
FROM
	good_page_tpl gpt
LEFT JOIN good_page_tpl_apply gpta ON gpta.tpl_id = gpt.id
LEFT JOIN (
	SELECT
		tm.tpl_id,
		group_concat(tm.tpl_area_name) AS model_name
	FROM
		good_page_tpl_model tm
	GROUP BY
		tm.tpl_id
) gptm ON gptm.tpl_id = gpt.id
where 1=1
and gpt.del_flag = 'F'
<#if tplName?? && tplName!="">
and gpt.tpl_name = '${tplName}'
</#if>
<#if applyScope?? && applyScope!="">
and gpta.apply_scope = '${applyScope}'
</#if>
<#if isValid?? && isValid !="">
and gpt.is_valid = '${isValid}'
</#if>
<#if sort?? && sort!="">
and gpta.sort = '${sort}'
</#if>
<#if tplClassCode?? && tplClassCode!="">
and gpt.tpl_class_code = '${tplClassCode}'
</#if>
LIMIT ?,?