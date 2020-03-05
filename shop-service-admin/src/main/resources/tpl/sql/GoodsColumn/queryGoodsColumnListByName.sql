SELECT
goods_column.id,
goods_column.shop_ID AS shopId,
goods_column.column_name AS columnName,
goods_column.rule_source_ID AS ruleSourceId,
goods_column.order_by AS orderBy,
goods_column.`status` AS status,
goods_column.showYn AS showyn,
goods_column.create_by AS createBy,
goods_column.create_time AS createTime,
goods_column.update_by AS updateBy,
goods_column.introduce_image AS introduceImage,
goods_column.slideshow_images AS slideshowImages,
image.id AS showpictureId,
image.pic_url AS showpicture
FROM
goods_column LEFT JOIN
image ON goods_column.showPicture=image.id
<#if columnName?? && columnName != "">
 WHERE goods_column.column_name LIKE  CONCAT("%","${columnName}","%")
 </#if>
  ORDER BY goods_column.order_by desc limit ?,?