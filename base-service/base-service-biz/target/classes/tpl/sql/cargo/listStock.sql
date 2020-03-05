select stock.* from (
	  select s.id id,s.cargo_sku_id sku_id,s.out_shelves_no outShelvesNo,s.on_sales_no onSalesNo,s.on_pay_no
	  onPayNo,s.on_send_no onSendNo,s.remain_count remainCount,(s.out_shelves_no+s.on_sales_no+s.on_pay_no+s.on_send_no) total,s.warning_value warningValue,s.is_valid isValid,s.on_shelves_no onShelvesNo,s.all_sell_no allSellNo,s.warning_state warningState,
	  s.is_sync isSync,k.code sku_code,c.name goods_name,c.cargo_no goods_code,f.name classify,b.name brand,s.create_time create_time,'0' as type
	  from cargo_sku_stock s,cargo_sku k,cargo c,cargo_classify f,cargo_brand b
	  where s.cargo_sku_id=k.id and k.cargo_id=c.id and c.classify_id=f.id and c.brand_id=b.id
	  UNION ALL
	  select s.id id,s.cargo_sku_id sku_id,s.out_shelves_no outShelvesNo,s.on_sales_no onSalesNo,s.on_pay_no
	  onPayNo,s.on_send_no onSendNo,s.remain_count remainCount,(s.out_shelves_no+s.on_sales_no+s.on_pay_no+s.on_send_no) total,s.warning_value warningValue,s.is_valid isValid,s.on_shelves_no onShelvesNo,s.all_sell_no allSellNo,s.warning_state warningState,
	  s.is_sync isSync,k.code sku_code,c.material_name goods_name,c.material_code goods_code,f.name classify,b.name brand,s.create_time create_time,'1' as type
	  from cargo_sku_stock s,cargo_sku k,purchase_material c,purchase_classify f,cargo_brand b
	  where s.cargo_sku_id=k.id and k.cargo_id=c.id and c.pur_classify_id=f.id and c.brand_id=b.id
	  ) stock where 1=1
	  <#if type??>
	   and stock.type like ${type}
	  </#if>
	  <#if classify??>
	  and stock.classify like ${classify}
	  </#if>
	  <#if brand??>
	  and stock.brand like ${brand}
	  </#if>
	  <#if keyWord??>
	  and stock.sku_code like ${skuCode} or stock.goods_name=${goodsName} or stock.goods_code like ${goodsCode}
	  </#if>
	  <#if warningState??>
	  and stock.warningState like ${warningState};
	  </#if>
   		order by stock.create_time desc limit ?,?;