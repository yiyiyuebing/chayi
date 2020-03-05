SELECT
	css.id as id,
	IFNULL(css.warning_value, 0) AS warning_value,
	(IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0)) AS currStock,
	css.warning_state,
	css.is_sync AS isSync,
	css.is_valid AS isValid,
	cs.`code` AS skuCode,
	cs.sku_name AS cargoSKuValue,
	css.update_time AS updateTime,
	cs. NAME AS cargoSkuName,
	c.cargo_no AS goodsCode,
	c.`name` AS goodsName,
	cc. NAME AS classify,
	cb. NAME AS brand,
	sum(IFNULL(pgs.on_sales_no, 0) + IFNULL(tgs.on_sales_no, 0)) as onSalesNo,
	sum(IFNULL(pgs.sale_num, 0) + IFNULL(tgs.sale_num, 0)) as saleNum
FROM
	cargo_sku_stock css
LEFT JOIN cargo_sku cs ON cs.id = css.cargo_sku_id
LEFT JOIN cargo c ON c.id = cs.cargo_id
LEFT JOIN cargo_classify cc ON cc.id = c.classify_id
LEFT JOIN cargo_brand cb ON cb.id = c.brand_id
left join purchase_goods_sku pgs on pgs.cargo_sku_id = cs.id
left join trade_good_sku tgs on tgs.cargo_sku_id = cs.id
WHERE
	1 = 1
	<#if classify?? && classify != '' && classify != '0'>
		and cc.id in (${classify})
	</#if>
	<#if brand?? && brand != ''>
	and cb.id = ${brand}
	</#if>
	<#if keyWord?? && keyWord != ''>
	and (
		cs.code like '%${keyWord}%' or c.cargo_no like '%${keyWord}%' or c.name like '%${keyWord}%'
	)
	</#if>
	<#if warningState?? && warningState != ''>
		<#if warningState == '1'>
			and ifnull(css.warning_value, 0) <= (IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0))
		</#if>
		<#if warningState != '1'>
			and ifnull(css.warning_value, 0) > (IFNULL(css.curr_stock, 0) + IFNULL(css.on_pay_no, 0))
		</#if>
  	</#if>
  	<#if isSync?? && isSync != ''>
		<#if isSync == '-1'>
			and (css.is_sync = ${isSync} or css.is_sync is null)
		</#if>
		<#if isSync != '-1'>
			and css.is_sync = ${isSync}
		</#if>
  	</#if>
  	group by css.id
	order by css.create_time desc limit ?, ?;
