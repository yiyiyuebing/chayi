SELECT
count(cargo_brand.id)
FROM
cargo_brand LEFT JOIN
image image1 ON cargo_brand.logo=image1.id LEFT JOIN image image2 ON
image2.id=cargo_brand.pc_logo WHERE del_flag='F'
<#if cargoBrandName?? && cargoBrandName != "">
 AND cargo_brand.`name`="${cargoBrandName}"
</#if>