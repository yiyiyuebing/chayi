SELECT
sys_dict.dict_id AS dictId,
sys_dict.parent_id AS parentId,
sys_dict.dict_type dictType,
sys_dict.`code`,
sys_dict.`value`,
sys_dict.order_num AS orderNum,
sys_dict.memo,
sys_dict.is_valid AS isValid,
sys_dict.date_created AS dateCreated,
sys_dict.last_updated AS lastUpdated,
sys_dict.dict_key
FROM
sys_dict WHERE sys_dict.dict_type="${dict_type}" AND sys_dict.del_flag='F' AND sys_dict.is_valid='T'
 <#if label?? && label != "">
 AND sys_dict.`value` LIKE  "%${label}%"
  </#if>
   ORDER BY sys_dict.order_num DESC