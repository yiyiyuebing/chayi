SELECT
sys_dict.dict_id AS dictid,
sys_dict.`value` AS valuelabel,
sys_dict.dict_type AS dictType
FROM
sys_dict where sys_dict.dict_type='label' AND sys_dict.del_flag='F'
 <#if label?? && label != "">
 AND sys_dict.`value` LIKE  "%${label}%"
  </#if>
