SELECT
	tg.id,
	tg. NAME,
	tg.subtitle,
	tgs.market_price,
	tgs.sale_price,
	tgs.min_tuan_num,
	tg.postid,
	tg.create_time,
	tg.sale_num,
	(tg.sale_num + tg.base_sale) AS sale_all_num,
	k.stock,
	p.storeNum,
	tg.begin_time,
	tg.end_time,
	gc.column_name,
	tg.post,
	tg.cargo_id,
	(
		SELECT
			i.pic_url
		FROM
			image i
		WHERE
			i.id = cargo.small_image_id
	) AS showPicture
		FROM trade_good tg
		LEFT JOIN goods_column gc ON tg.category = gc.id
		LEFT JOIN cargo ON tg.cargo_id = cargo.id
        LEFT JOIN carriage_rule ru ON ru.id = tg.postid
		LEFT JOIN (SELECT id,good_id,max(market_price) market_price,min(sale_price) sale_price,min_tuan_num from trade_good_sku GROUP BY good_id) tgs ON tg.id = tgs.good_id
		LEFT JOIN good_evaluation ge ON ge.id = tgs.id
		LEFT JOIN (select sum(nums) stock,good_id from trade_good_sku group by good_id) k ON k.good_id=tg.id
		LEFT JOIN (select count(*) storeNum,goods_id from shopping_cart where opertion_type=1 group by goods_id) p ON p.goods_id=tg.id
		where tg.id=${goodId}