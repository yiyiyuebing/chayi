
<#if purchseOrTrade == 'trade'>
SELECT
tj_trade_total_kpi.id,
SUM(tj_trade_total_kpi.total_pay) AS totalPay,
SUM(tj_trade_total_kpi.visitor_num) AS visitorNum,
SUM(tj_trade_total_kpi.pv) AS pv,
SUM(tj_trade_total_kpi.buyer_num)AS BuyerNum,
SUM(tj_trade_total_kpi.order_num) AS orderNum,
SUM(tj_trade_total_kpi.shop_num) AS shopNum,
SUM(tj_trade_total_kpi.cvr) AS cvr,
SUM(tj_trade_total_kpi.pct) AS pct,
SUM(tj_trade_total_kpi.tkje) AS tkje
FROM
tj_trade_total_kpi WHERE 1=1
  <#if startDate?? && startDate?length gt 0>  AND tj_trade_total_kpi.date_created >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tj_trade_total_kpi.date_created <= '${endDate}' </#if>
 </#if>
 <#if purchseOrTrade == 'purchase'>
 SELECT
tj_purchase_total_kpi.id,
SUM(tj_purchase_total_kpi.total_pay) AS totalPay,
SUM(tj_purchase_total_kpi.visitor_num) AS visitorNum,
SUM(tj_purchase_total_kpi.pv) AS pv,
SUM(tj_purchase_total_kpi.buyer_num)AS BuyerNum,
SUM(tj_purchase_total_kpi.order_num) AS orderNum,
SUM(tj_purchase_total_kpi.shop_num) AS shopNum,
SUM(tj_purchase_total_kpi.cvr) AS cvr,
SUM(tj_purchase_total_kpi.pct) AS pct,
SUM(tj_purchase_total_kpi.tkje) AS tkje
FROM
tj_purchase_total_kpi WHERE 1=1
  <#if startDate?? && startDate?length gt 0>  AND tj_purchase_total_kpi.date_created >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tj_purchase_total_kpi.date_created <= '${endDate}' </#if>
  </#if>