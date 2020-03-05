select * from cargo_sku cs where 1=1
<#if cargoId??>
    and cs.cargo_id = ${cargoId}
</#if>
<#if keyword??>
    and cs.code like '%${keyword}%' or cs.sku_name like '%${keyword}%' or cs.memo like '%${keyword}%'
</#if>

;