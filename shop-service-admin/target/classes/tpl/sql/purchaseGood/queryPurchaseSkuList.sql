  SELECT
    purchase_goods_sku.id,
    purchase_goods.pur_goods_name AS NAME,
    cargo.cargo_no AS cargoNo,
    cargo_sku.`code`,
    cargo_sku. NAME AS skuName,
    purchase_goods.id AS purchaseGoodsId,
    cargo_sku.retail_price AS retailPrice,
    purchase_goods.group_name AS classifyName,
    cargo_brand.`name` AS brandName,
    goodstheme.themename AS themeName,
    purchase_goods.order_index AS sort,
    CASE
  WHEN good_putaway_schedule.is_valid = 'T' THEN
    4
  ELSE
    purchase_goods_sku.`status`
  END STATUS,
   purchase_goods_sku.on_sales_no AS nums,
   goodsColumn.column_name AS columnName,
   purchase_goods.update_time AS updateTime,
   sys_dict.`value` AS label,
   cargo.id AS cargoId,
   purchase_goods_sku.cargo_sku_id
  FROM
    purchase_goods_sku
  LEFT JOIN purchase_goods ON purchase_goods_sku.pur_goods_id = purchase_goods.id
  LEFT JOIN cargo_sku ON cargo_sku.id = purchase_goods_sku.cargo_sku_id
  LEFT JOIN purchase_classify ON purchase_goods.pur_classify_id = purchase_classify.id
  LEFT JOIN cargo ON cargo.id = cargo_sku.cargo_id
  LEFT JOIN cargo_brand ON cargo.brand_id = cargo_brand.id
  LEFT JOIN sys_dict ON sys_dict.dict_id = purchase_goods.label
  LEFT JOIN good_putaway_schedule ON purchase_goods_sku.id = good_putaway_schedule.sku_id
  AND good_putaway_schedule.del_flag = 'F'
  AND good_putaway_schedule.is_valid = 'T'
  AND good_putaway_schedule.is_success IS NULL
  LEFT JOIN (
    SELECT
      t.id,
      (
        SELECT
          GROUP_CONCAT(c.`name`)
        FROM
          vtwo_good_theme c
        WHERE
          FIND_IN_SET(c.id, t.theme)
        AND c.`status` = 1
      ) AS themename
    FROM
      purchase_goods t
  ) goodstheme ON goodstheme.id = purchase_goods.id
  LEFT JOIN (
    SELECT
      t.id,
      (
        SELECT
          GROUP_CONCAT(c.column_name)
        FROM
          goods_column c
        WHERE
          FIND_IN_SET(c.id, t.category)
        AND c.`status` = 1
      ) AS column_name
    FROM
      purchase_goods t
  ) goodsColumn ON goodsColumn.id = purchase_goods.id
  WHERE
    1 = 1
<#if skuName?? && skuName!="">
  and (cargo_sku.code like '%${skuName}%'
  or purchase_goods.pur_goods_name like '%${skuName}%'
  or cargo_sku.name like '%${skuName}%'
  or cargo.cargo_no like '%${skuName}%'
  or purchase_goods.id like '%${skuName}%' )
</#if>
<#if label?? && label!= "">
  and purchase_goods.label = '${label}'
</#if>
<#if status?? && status!="">
  <#if status="4">
     AND good_putaway_schedule.is_valid = 'T' AND good_putaway_schedule.del_flag = 'F'
  <#else>
     and purchase_goods_sku.status = '${status}'
  </#if>
</#if>
<#if columnName?? && columnName!="">
  and FIND_IN_SET ('${columnName}',purchase_goods.category)
</#if>
<#if themeName?? && themeName!="">
  and FIND_IN_SET ('${themeName}',purchase_goods.theme)
</#if>
<#if classifyName?? && classifyName!="-1" && classifyName!="">
  and (${classifyName})
</#if>
<#if brandName ?? && brandName!="-1" && brandName!="">
  and cargo.brand_id = '${brandName}'
</#if>
<#if isStore ?? && isStore!="">
  and purchase_goods.is_store = '${isStore}'
</#if>
order by purchase_goods.order_index desc