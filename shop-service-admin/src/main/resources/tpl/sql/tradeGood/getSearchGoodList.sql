SELECT
        tg.id,
      	tg.NAME,
      	tg.subtitle,
      	tgs.market_price as marketPrice,
      	tgs.sale_price as salePrice,
      	tgs.min_tuan_num as minTuanNum,
	      tg.postid,
      	tg.create_time as createTime,
	      tg.sale_num as saleNum,
      	(tg.sale_num + tg.base_sale) as totalSaleNum,
      	k.stock,
      	p.storeNum,
	      tg.begin_time as beginTime,
      	tg.end_time as endTime,
      	gc.column_name as columnName,
	      tg.post,
	      tg.cargo_id as cargoId,
	      tgs.id as goodSkuId,
        (select i.pic_url from image i where i.id=cargo.small_image_id) as showPicture
        FROM trade_good tg
        LEFT JOIN goods_column gc ON tg.category = gc.id
        LEFT JOIN cargo ON tg.cargo_id = cargo.id
        LEFT JOIN (SELECT id,good_id,max(market_price) market_price,min(sale_price) sale_price,min_tuan_num from trade_good_sku GROUP BY good_id) tgs ON tg.id = tgs.good_id
        LEFT JOIN (select sum(nums) stock,good_id from trade_good_sku group by good_id) k ON k.good_id=tg.id
        LEFT JOIN (select count(*) storeNum,goods_id from shopping_cart where opertion_type=1 group by goods_id) p ON p.goods_id=tg.id
        <#if info.labelId?? && info.labelId != "">
            LEFT JOIN good_labels ON good_labels.good_id=tg.id
        </#if>
        where tg.status = 1 and (gc.showYn is null or gc.showYn="")
        <#if info.shopId?? && info.shopId != "">
            and not exists(select * from store_subbranch_good_soldout where tg.id=good_id and subranch_id=${info.shopId})
        </#if>
        <#if info.category?? && info.category !="">
            AND tg.category like "%${info.category}%"
        </#if>
        <#if info.theme?? && info.theme !="">
            AND tg.theme like "%${info.theme}%"
        </#if>
        <#if info.cargoNo?? && info.cargoNo !="">
            AND cargo.cargo_no like "%${info.cargoNo}%"
        </#if>
        <#if info.startPrice?? && info.startPrice!="">
            and tgs.sale_price&gt=${info.startPrice}
        </#if>
        <#if info.endPrice?? && info.endPrice!="">
            and tgs.sale_price&lt=${info.endPrice}
        </#if>
        <#if info.classifyId?? && info.classifyId != "">
            and cargo.classify_id in (${info.classifyId})
        </#if>
        <#if info.brandId?? && info.brandId!="">
            and cargo.brand_id=${info.brandId}
        </#if>
        <#if info.goodName?? && info.goodName!="">
            and tg.name like "%${info.goodName}%"
        </#if>
        <#if info.columnId?? && info.columnId!="">
            and gc.id =${info.columnId}
        </#if>
        GROUP BY tg.id
        ORDER BY
        <#if info.saleNumSort?? && info.saleNumSort!="">
            <#if info.saleNumSort == "0">
                tg.sale_num desc,
            </#if>
            <#if info.saleNumSort == "1">
                tg.sale_num asc,
            </#if>
        </#if>
        <#if info.priceSort?? && info.priceSort!="">
            <#if info.priceSort == "0">
                tgs.sale_price desc,
            </#if>
            <#if info.priceSort == "1">
                tgs.sale_price asc,
            </#if>
        </#if>
        tg.sort desc,tg.create_time desc LIMIT ${(info.pageNum - 1) * info.pageSize},${info.pageSize}