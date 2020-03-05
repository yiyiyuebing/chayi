SELECT
count(*)
FROM
goods_column LEFT JOIN
image ON goods_column.showPicture=image.id
<#if columnName?? && columnName != "">
 WHERE goods_column.column_name LIKE "%${columnName}%"
 </#if>