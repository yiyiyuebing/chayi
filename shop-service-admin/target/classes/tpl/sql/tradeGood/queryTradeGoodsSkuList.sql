	SELECT
		trade_good_sku.id,
		trade_good.`name`,
		cargo.cargo_no AS cargoNo,
		cargo_sku.`code` AS CODE,
		cargo_sku. NAME AS skuName,
		trade_good.id AS tradeGoodId,
		cargo_sku.retail_price AS retailPrice,
		trade_good.`group_name` AS cargoClassifyName,
		cargo_brand.`name` AS cargoBrand,
		trade_good.sort AS sort,
		trade_good_sku.on_sales_no AS nums,
		goodsColumn.column_name AS columnName,
		goodstheme.themename AS themeName,
		sys_dict.`value` AS goodsLabel,
		CASE
	WHEN good_putaway_schedule.is_valid = 'T' THEN
		4
	ELSE
		trade_good_sku.`status`
	END STATUS,
	 trade_good.update_time AS updateTime,
	 cargo.id AS cargoId,
	 trade_good_sku.cargo_sku_id AS cargoSkuId
	FROM
		trade_good_sku
	LEFT JOIN trade_good ON trade_good.id = trade_good_sku.good_id
	LEFT JOIN sys_dict ON sys_dict.dict_id = trade_good.label
	LEFT JOIN cargo ON cargo.id = trade_good.cargo_id
	LEFT JOIN cargo_sku ON cargo_sku.id = trade_good_sku.cargo_sku_id
	LEFT JOIN cargo_classify ON cargo_classify.id = cargo.classify_id
	LEFT JOIN cargo_brand ON cargo.brand_id = cargo_brand.id
	LEFT JOIN good_putaway_schedule ON trade_good_sku.id = good_putaway_schedule.sku_id
	AND good_putaway_schedule.del_flag = 'F'
	AND good_putaway_schedule.is_valid = 'T'
	AND good_putaway_schedule.is_success IS NULL
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
			trade_good t
	) goodsColumn ON goodsColumn.id = trade_good.id
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
			trade_good t
	) goodstheme ON goodstheme.id = trade_good.id
	WHERE
		1 = 1
	<#if skuName?? && skuName!="">
	   and (trade_good.name like '%${skuName}%' or cargo_sku.name like '%${skuName}%'
	    or cargo.cargo_no like '%${skuName}%' or trade_good.id like '%${skuName}%' or cargo_sku.code like '%${skuName}%')
	</#if>
  <#if goodsLabel?? && goodsLabel!="">
     and trade_good.label = '${goodsLabel}'
  </#if>
  <#if status?? && status!="">
     <#if status=="4">
         AND good_putaway_schedule.is_valid = 'T' AND good_putaway_schedule.del_flag = 'F'
     <#else>
        and trade_good_sku.status = '${status}'
     </#if>
  </#if>
  <#if columnName?? && columnName!="">
      and FIND_IN_SET ('${columnName}',trade_good.category)
  </#if>
  <#if themeName?? && themeName!="">
      and FIND_IN_SET ('${themeName}',trade_good.theme)
  </#if>
  <#if cargoClassIfyName?? && cargoClassIfyName!="" && cargoClassIfyName!="-1">
      and (${cargoClassIfyName})
  </#if>
  <#if cargoBrand?? && cargoBrand!="" && cargoBrand!="-1">
      and cargo.brand_id = '${cargoBrand}'
  </#if>
  <#if isStore?? && isStore!="">
      and trade_good.is_store = '${isStore}'
  </#if>
  order by trade_good.sort desc