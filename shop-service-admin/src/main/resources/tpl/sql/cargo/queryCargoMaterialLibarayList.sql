select * from cargo_material_library cml
<#if cargoName?? && cargoName != '' || cargoNum?? && cargoNum != ''>
 LEFT JOIN cargo c ON cml.relevance_cargo_id=c.id
</#if>
where 1=1
<#if materialName??>
    and cml.material_name like '%${materialName}%'
</#if>
<#if isValid??>
    and cml.is_valid = '${isValid}'
</#if>
<#if cargoName?? && cargoName != ''>
  AND c.`name` LIKE  '%${cargoName}%'
</#if>
<#if cargoNum?? && cargoNum != ''>
  AND c.cargo_no LIKE  '%${cargoNum}%'
</#if>
 order by cml.date_created desc limit ?,?