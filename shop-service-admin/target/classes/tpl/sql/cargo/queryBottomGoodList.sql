SELECT
	gpt.id,
	gptm.model_name AS modelName,
	CASE
WHEN gptm.good_ids IS NULL THEN
	0
ELSE
	length(gptm.good_ids) - length(
		REPLACE (gptm.good_ids, ',', '')
	) + 1
END AS num,
 gptm.date_created AS lastUpdated
FROM
	good_page_tpl gpt,
	good_page_tpl_model gptm
WHERE
	gpt.id = gptm.tpl_id
AND gpt.del_flag = 'F'
AND gpt.tpl_class_code = 'mobileb2bbottom'
and gptm.del_flag = 'F'