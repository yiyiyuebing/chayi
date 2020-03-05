SELECT
	*
FROM
	index_module
WHERE
	is_valid = "T"
AND del_flag = "F"
AND module_type = "${type}"
<#if classifyIds?? && classifyIds != "">
  AND (classify_id IN (${classifyIds}) OR classify_id IS NULL)
</#if>
ORDER BY
	sort DESC