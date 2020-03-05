select DISTINCT
        pg.id,
        pg.pur_classify_id,
        pg.pur_material_id,
        pg.brand_id,
        pg.pur_goods_name,
        pg.base_sale_num,
        pg.goods_profits,
        pg.theme,
        pg.is_sample,
        pg.postid,
        pg.status,
        pg.sale_num,
        pg.order_index,
        pg.goods_introduction,
        pc.name as name,
        cb.name as brandName ,
        pm.material_code as purGoodsCode,
        pm.material_name as materialName,
        pm.material_code as materialCode,
        pga.flavor,
        pga.taste
        from purchase_goods pg
        left join purchase_classify pc on pg.pur_classify_id = pc.id
        left join cargo_brand cb on pg.brand_id = cb.id
        left join purchase_material pm on pg.pur_material_id = pm.id
        left join purchase_goods_attr pga on pga.pur_goods_id = pg.id
        where pg.id = ?