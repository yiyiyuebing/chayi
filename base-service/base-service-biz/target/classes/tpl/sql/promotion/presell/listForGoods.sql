SELECT
    a.id,
    good_id,
    sku_id AS good_sku_id,
    'presell' AS activity_type,
    '预售' AS activity_name,
    b.name AS activity_full_name,
    0 AS discount_amount,
    tag1,
    tag2,
    tag1_valid,
    tag2_valid,
    presell_start as activity_start,
    presell_end as activity_end,
    presell_type,
    payment_start,
    payment_end,
    ship_time,
    presell_desc,
    presell_agreement,
    limit_flg,
    limit_num,
    limit_unit,
    first_amount,
    remaining_amount,
    presell_amount,
    vm_count,
    a.presell_num,
    a.sale_num
FROM
    sp_presell_good a,
    sp_presell_activity b
WHERE
    a.activity_id = b.id
        and a.is_valid = 'T'
        and a.del_flag = 'F'
        and b.is_valid = 'T'
        and b.del_flag = 'F'
        and b.presell_end > now()
        and a.sku_id in (%s)