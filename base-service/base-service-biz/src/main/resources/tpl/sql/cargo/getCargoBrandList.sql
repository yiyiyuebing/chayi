select
		b.id,
		(select i.pic_url from Image i where i.id=logo) as logo,
		b.name,url, b.supplier_name, b.brand_recommendation,
		b.create_time,
		b.create_by,
		b.update_time, b.update_by
        from trade_good tg
        LEFT JOIN cargo ON tg.cargo_id = cargo.id
        LEFT JOIN cargo_brand b ON b.id= cargo.brand_id
        <#if category?? && category !="">
            LEFT JOIN goods_column gc ON tg.category = gc.id
            left join cargo_classify on cargo_classify.id=cargo.classify_id
        </#if>
        WHERE  tg.status=1
        <#if classify?? && classify != "">
            and cargo.classify_id in (${classify})
        </#if>
        <#if category?? && category !="">
            AND FIND_IN_SET(${category},tg.category)
        </#if>
        GROUP BY  cargo.brand_id
        ORDER BY b.sort DESC