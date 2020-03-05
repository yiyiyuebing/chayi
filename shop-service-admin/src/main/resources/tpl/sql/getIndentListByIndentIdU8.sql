select '0' as isPur,id, indent_id, trade_good_sku_id, `number`, final_amount, trade_good_name,
    trade_good_img_url, trade_good_amount, trade_good_type,supply_price,cargo_sku_id, gift_flag,
    (select c.cargo_no FROM cargo c , cargo_sku cs , trade_good_sku tgs where tgs.cargo_sku_id
    = cs.id and c.id = cs.cargo_id and indent_list.trade_good_sku_id = tgs.id) as cargo_no, '0' as is_sample
    from indent_list
    where indent_id = ?
    UNION ALL
    select '1' as isPur,
    id, order_id, pur_goods_sku_id as trade_good_sku_id,  `number`, final_amount, pur_goods_name as trade_good_name, pur_goods_img_url as trade_good_img_url,
    pur_goods_amount as trade_good_amount, pur_goods_type as trade_good_type,supply_price,material_sku_id as cargo_sku_id, 'F' AS gift_flag,
    (select c.material_code FROM purchase_material c , cargo_sku cs , purchase_goods_sku tgs where tgs.material_sku_id = cs.id and c.id = cs.cargo_id and purchase_order_list.pur_goods_sku_id = tgs.id) as cargo_no, is_sample
    FROM purchase_order_list
    where order_id = ?