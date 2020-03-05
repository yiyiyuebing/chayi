select count(DISTINCT css.id)
	from cargo_sku_stock css
	left join cargo_sku cs on cs.id = css.cargo_sku_id
	left join cargo c on c.id = cs.cargo_id
	left join cargo_classify cc on cc.id = c.classify_id
	left join cargo_brand cb on cb.id = c.brand_id
	left join purchase_goods_sku pgs on pgs.cargo_sku_id = cs.id
	left join trade_good_sku tgs on tgs.cargo_sku_id = cs.id
	where 1=1 and (c.del_flag != 'T')
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
			and ifnull(css.warning_value, 0) <= css.curr_stock
		</#if>
		<#if warningState != '1'>
			and ifnull(css.warning_value, 0) > css.curr_stock
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