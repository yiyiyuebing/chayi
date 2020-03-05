select count(*) from (
	  select s.id id,s.cargo_sku_id sku_id,s.out_shelves_no no_shelve,s.on_sales_no shelve_noshale,s.on_pay_no
	  shelve_nopay,s.on_send_no shelve_nosend,s.remain_count remain_count,(s.out_shelves_no+s.on_sales_no+s.on_pay_no+s.on_send_no) total,s.warning_value,s.is_valid,s.on_shelves_no,s.all_sell_no,s.warning_state,k.code sku_code,c.name goods_name,c.cargo_no goods_code,f.name classy_name,b.name brand_name,s.create_time,'0' as type
	  from cargo_sku_stock s,cargo_sku k,cargo c,cargo_classify f,cargo_brand b
	  where s.cargo_sku_id=k.id and k.cargo_id=c.id and c.classify_id=f.id and c.brand_id=b.id
	  UNION ALL
	  select s.id id,s.cargo_sku_id sku_id,s.out_shelves_no no_shelve,s.on_sales_no shelve_noshale,s.
	  on_pay_no shelve_nopay,s.on_send_no shelve_nosend,s.remain_count remain_count,(s.out_shelves_no+s.on_sales_no+s.on_pay_no+s.on_send_no) total,s.warning_value,s.is_valid,s.on_shelves_no,s.all_sell_no,s.warning_state,k.code sku_code,c.material_name goods_name,c.
	  material_code goods_code,f.name classy_name,b.name brand_name,s.create_time,'1' as type
	  from cargo_sku_stock s,cargo_sku k,purchase_material c,purchase_classify f,cargo_brand b
	  where s.cargo_sku_id=k.id and k.cargo_id=c.id and c.pur_classify_id=f.id and c.brand_id=b.id
	  ) stock where 1=1
	  <#if type??>
	   and stock.type like ${type}
	  </#if>
	  <#if classify??>
	  and stock.classy_name like ${classify}
	  </#if>
	  <#if brand??>
	  and stock.brand_name like ${brand}
	  </#if>
	  <#if keyWord??>
	  and stock.sku_code like ${skuCode} or stock.goods_name=${goodsName} or stock.goods_code like ${goodsCode}
	  </#if>
	  <#if warningState??>
	  and stock.warning_state like ${warningState};
	  </#if>
   		order by stock.create_time desc;