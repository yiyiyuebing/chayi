select DISTINCT
        pg.id,
        pg.pur_classify_id,
        pg.pur_goods_name,
        pg.base_sale_num,
        pg.goods_profits,
        pg.theme,
        pg.is_sample,
        pg.postid,
        pg.status,
        pg.order_index,
        pg.sale_num,
        css.supply_price as supplyPrice ,
        css.unit,
				sd.value as label,
        (select i.pic_url from image i where i.id = c.small_image_id) as smallImage,
        (pg.base_sale_num+pg.sale_num) as totalSaleNum,
				pgs.id as skuId
        from purchase_goods pg
        join cargo c on c.id = pg.cargo_id
				left join (select id, pur_goods_id, cargo_sku_id from (select id, pur_goods_id, cargo_sku_id from purchase_goods_sku order by sale_num desc) a GROUP BY a.pur_goods_id) pgs on pgs.pur_goods_id = pg.id
        join cargo_sku_supply_price css on css.cargo_sku_id = pgs.cargo_sku_id
        <#if query.storeLevelId?? && query.storeLevelId != "">
            and css.store_level_id = "query.storeLevelId"
        </#if>
				left join sys_dict sd on sd.dict_id = pg.label
				<#if query.classifyAttrIds?? && query.classifyAttrIds != "">
				    join (select pma.* from purchase_material_attr pma join purchase_classify_attr pca on pma.pur_classify_attr_id = pca.id
				    where pca.id in (${query.classifyAttrIds}) group by pma.pur_goods_id) pmac on pmac.pur_goods_id = pg.id
				</#if>
        where pg.status=1
        <#if query.purGoodsName?? && query.purGoodsName !="">
            and pg.pur_goods_name like "%${query.purGoodsName}%"
        </#if>
        <#if classifyList?size gt 0>
            and (
            <#list classifyList as classify>
              <#if classify_index gt 0>
                or
              </#if>
                pg.group_id like "%${classify}%"
            </#list>
            )
        </#if>
        <#if query.brandIds?? && query.brandIds != "">
            and c.brand_id in (${query.brandIds})
        </#if>
        <#if query.minPrice?? && query.minPrice!="" >
            and css.supply_price >=${query.minPrice}
        </#if>
        <#if query.maxPrice?? && query.maxPrice!="" >
            and css.supply_price <=${query.maxPrice}
        </#if>
        ORDER BY
        <#if query.priceSort?? && query.priceSort!="">
            <#if query.priceSort=="0">
                css.supply_price desc
            </#if>
            <#if query.priceSort=="1">
                css.supply_price asc
            </#if>
        </#if>
        <#if query.saleNumSort?? && query.saleNumSort!="">
            <#if query.saleNumSort=="0">
                totalSaleNum desc
            </#if>
            <#if query.saleNumSort=="1">
                totalSaleNum asc
            </#if>
        </#if>
        <#if query.orderIndex?? && query.orderIndex!="">
            <#if query.orderIndex=="0">
                pg.order_index desc
            </#if>
            <#if query.orderIndex=="1">
                pg.order_index asc
            </#if>
        </#if>
        LIMIT ${(query.pageNum - 1) * query.pageSize},${query.pageSize}