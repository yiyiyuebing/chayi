select
 i.id, i.order_no, i.receiver, i.receiver_phone as receiverPhone, concat(i.province, i.city, i.district, i.address) as address,
 i.invoice_content as invoiceContent, i.invoice_name as invoiceName,  i.buyer_carriage as buyerCarriage,
 i.payment_amount as paymentAmount, i.buyer_remark as buyerRemark, 's' as buyType,
 i.status, i.create_time as createTime, i.pay_time as payTime, i.ship_time as shipTime, i.type,
 i.stage_order as stageOrder, i.pre_amount as preAmount, i.earnest_amount as earnestAmount,
 i.last_amount as lastAmount, i.last_earnest_amount as last_earnest_amount,
 i.express_company as expressCompany, i.express_number as expressNumber, i.pay_type as payType,
 i.pay_account as payAccount, i.remark as remarkInfo,
 b.user_name as buyerName,
 (SELECT id FROM store_subbranch WHERE id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) AS subbranchId,
 (select ss.department_num from store_subbranch ss where ss.id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) as storeDeptNum,
 (select ss.number from store_subbranch ss where ss.id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) as storeNum,
 (select ss.name from store_subbranch ss where ss.id = (CASE WHEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id AND is_sub_account = 'T') is NOT NULL THEN (SELECT parent_subranch_id FROM store_subbranch WHERE id = i.subbranch_id ) ELSE ( SELECT id FROM store_subbranch WHERE id = i.subbranch_id) end)) as storeName,
 (select ss.user_name from store_subbranch ss where ss.id = i.subbranch_id and ss.is_sub_account = 'T') as userName,
 (select ss.mobile from store_subbranch ss where ss.id = i.subbranch_id and ss.is_sub_account = 'T') as mobile,
 (select sd.value from sys_dict sd where sd.code = i.express_id) as postMethod
from
purchase_order i
LEFT JOIN store_subbranch s ON s.id=i.subbranch_id
left join purchase_order_list il on il.order_id = i.id
left join cargo_sku cs on il.material_sku_id = cs.id
LEFT JOIN store_subbranch b on b.id = i.buyer_id
where 1=1
      <#if goodSku??> and cs.code like concat('%', '${goodSku ! ''}', '%') </#if>
      <#if goodName??> and il.pur_goods_name like concat('%', '${goodName ! ''}', '%') </#if>
      <#if orderNo??> and i.order_no like concat('%', '${orderNo ! ''}', '%') </#if>
      <#if buyerName??> and b.user_name like concat('%', '${buyerName ! ''}', '%') </#if>
      <#if storeName??> and s.name like concat('%', '${storeName ! ''}', '%') </#if>
      <#if storeNum??> and s.number = ${storeNum}</#if>
      <#if storeDeptNum??> and s.department_num = ${storeDeptNum} </#if>
      <#if receiver??> and i.receiver like concat('%', '${receiver ! ''}', '%') </#if>
      <#if receiverPhone??> and i.receiver_phone like concat('%', '${receiverPhone ! ''}', '%') </#if>
      <#if indentStatusStr??> and i.status in (${indentStatusStr}) </#if>
      <#if startTimeStr??> and i.create_time >= '${startTimeStr}' </#if>
      <#if endTimeStr??>  and i.create_time <= '${endTimeStr}' </#if>
      <#if type??> and i.type = '${type}' </#if>
      <#if dealStatus??> and i.deal_status = ${dealStatus} </#if>
      <#if isRemarkInfo??> and i.remark_level is not null </#if>
      <#if afterSaleService??> and il.status = '${afterSaleService}'  </#if>
      <#if province??> and s.province_name like concat('%', '${province ! ''}', '%')</#if>
      <#if city??> and s.city_name like concat('%', '${city ! ''}', '%') </#if>
      <#if country??> and s.country_name like concat('%', '${country ! ''}', '%') </#if>
group by i.id order by i.create_time desc;