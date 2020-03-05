select count(0) from cargo_material_library cml where 1=1
<#if materialName??>
    and cml.material_name like '%${materialName}%'
</#if>
<#if isValid??>
    and cml.is_valid = '${isValid}'
</#if>
