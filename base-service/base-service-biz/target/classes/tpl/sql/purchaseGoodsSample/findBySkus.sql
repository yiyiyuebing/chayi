SELECT 
    a.*, b.id AS good_sku_id
FROM
    purchase_goods_sample a,
    purchase_goods_sku b
WHERE
    a.pur_goods_id = b.pur_goods_id
        AND b.id IN (${skuIds})