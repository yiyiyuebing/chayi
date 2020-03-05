select i.id, i.`name`,i.subbranch_id as shop_id,i.total_amount,i.payment_amount,i.create_time,i.`number` as count, express_number, express_company,
        i.type,i.receiver,i.status,i.carriage,i.buyer_carriage,b.status AS indent_status, b.status AS billStatus
		from
		indent i LEFT JOIN indent_bill b ON i.id=b.indent_id
        where i.subbranch_id in (${shopIds})
        <#if buyerId??>
        and i.buyer_id = ${buyerId}
        </#if>
        <#if status??>
        and i.status = ${status}
        </#if>
         group by i.id
        order by i.create_time desc limit ?,?