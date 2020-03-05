SELECT
	imm.id,
	imm.classify_id AS classifyId,
	imm.module_name AS moduleName,
	imm.is_valid AS isValid,
	imm.sort,
	ms.subClassify,
	pc.`name` AS classifyName,
	imm.date_created as dataCreated
FROM
	index_mobile_module imm
LEFT JOIN purchase_classify pc ON imm.classify_id = pc.id
AND pc.del_flag = 'F'
LEFT JOIN (
	SELECT
		immc.module_id,
		group_concat(pcc.`name`) AS subClassify
	FROM
		index_mobile_module_classify immc,
		purchase_classify pcc
	WHERE
		pcc.id = immc.sub_classify_id
	AND pcc.del_flag = 'F' and immc.del_flag = 'F'
	GROUP BY
		immc.module_id
) AS ms ON ms.module_id = imm.id
WHERE
	imm.del_flag = 'F'
	order by imm.sort desc