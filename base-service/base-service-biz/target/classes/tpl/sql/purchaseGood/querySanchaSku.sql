select 	sku.id, sku.cargo_sku_id from purchase_goods goods, purchase_goods_sku sku, cargo
where 	goods.id = sku.pur_goods_id
and 	sku.id in (${skuIds})
and cargo.id = goods.cargo_id
and cargo.is_sancha = "T"