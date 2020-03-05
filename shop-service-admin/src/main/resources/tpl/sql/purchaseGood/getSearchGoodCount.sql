select
        count(DISTINCT pg.id)
        from purchase_goods pg
        join cargo c on c.id = pg.cargo_id
				left join (select id, pur_goods_id, cargo_sku_id from (select id, pur_goods_id, cargo_sku_id from purchase_goods_sku order by sale_num desc) a GROUP BY a.pur_goods_id) pgs on pgs.pur_goods_id = pg.id
        join cargo_sku_supply_price css on css.cargo_sku_id = pgs.cargo_sku_id
        <#if query.storeLevelId?? && query.storeLevelId != "">
            and css.store_level_id = "query.storeLevelId"
        </#if>
				left join sys_dict sd on sd.dict_id = pg.label
				<#if query.classifyAttrs?? && query.classifyAttrs != "">
				    join (select pma.* from purchase_material_attr pma join purchase_classify_attr pca on pma.pur_classify_attr_id = pca.id
				    where pca.name in (${query.classifyAttrs}) group by pma.pur_goods_id) pmac on pmac.pur_goods_id = pg.id
				</#if>
        where pg.status=1
        <#if query.purGoodsName?? && query.purGoodsName !="">
            and pg.pur_goods_name like "%${query.purGoodsName}%"
        </#if>
        <#if query.classifyIds?? && query.classifyIds != "">
            and pg.pur_classify_id in (${query.classifyIds})
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