SELECT
	im.id,
	im.module_name as modelName,
	im.sort,
	g.num,
	im.last_updated as lastUpdated
FROM
	index_module im
LEFT JOIN (
	SELECT
		img.module_id,
		count(*) AS num
	FROM
		index_module_good img where img.del_flag = 'F'
	GROUP BY
		img.module_id
) AS g ON g.module_id = im.id
where 1=1 and im.del_flag = 'F' and im.module_type = 're'
<#if moduleName?? && moduleName!="">
	and im.module_name = '${moduleName}'
</#if>