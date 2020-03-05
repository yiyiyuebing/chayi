SELECT 
    good_id,
    sku_id AS good_sku_id,
    'presell' AS activity_type,
    '预售' AS activity_name,
    0 AS discount_amount,
    presell_start as activity_start,
    presell_end as activity_end,
    presell_type,
    payment_start,
    payment_end,
    presell_agreement,
    limit_flg,
    limit_num,
    first_amount,
    remaining_amount,
    vm_count
FROM
    sp_presell_good a,
    sp_presell_activity b
WHERE
    a.activity_id = b.id
        AND b.presell_end > NOW()
        and a.is_valid = 'T'
        and a.sku_id in (%s)