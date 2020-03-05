select * from cargo_material_library cml where 1=1
<#if materialName??>
    and cml.material_name like '%${materialName}%'
</#if>
<#if isValid??>
    and cml.is_valid = '${isValid}'
</#if>
 order by cml.date_created desc limit ?,?