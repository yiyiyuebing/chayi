SELECT 
    COALESCE(s.supply_price, 0) AS supplyPrice
FROM
    store_subbranch ssub
        LEFT JOIN
    cargo_sku_supply_price s ON s.cargo_sku_id = (SELECT 
            cargo_sku_id
        FROM
            purchase_goods_sku
        WHERE
            id = ?)
        AND ssub.level_id = s.store_level_id
WHERE
    ssub.id = ?
    
