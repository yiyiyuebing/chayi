SELECT
cargo_brand.id,
cargo_brand.`name`,
cargo_brand.supplier_name AS supplierName,
cargo_brand.brand_recommendation AS brandRecommendation,
cargo_brand.create_time AS createTime,
cargo_brand.create_by AS createBy,
cargo_brand.update_time AS updateTime,
cargo_brand.update_by AS updateBy,
cargo_brand.sort,
cargo_brand.order_by AS orderBy,
cargo_brand.url,
image1.pic_url AS logo,
image2.pic_url AS pcLogo
FROM
cargo_brand LEFT JOIN
image image1 ON cargo_brand.logo=image1.id LEFT JOIN image image2 ON
image2.id=cargo_brand.pc_logo WHERE del_flag='F'
<#if cargoBrandName?? && cargoBrandName != "">
 AND cargo_brand.`name` LIKE  "%${cargoBrandName}%"
</#if>
 ORDER BY cargo_brand.sort DESC  LIMIT ?,?
