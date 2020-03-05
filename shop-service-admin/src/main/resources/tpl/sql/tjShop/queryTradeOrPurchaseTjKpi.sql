<#if purchseOrTrade == 'trade'>
SELECT
tj_trade_total_kpi.id,
tj_trade_total_kpi.stat_date AS statDate,
tj_trade_total_kpi.total_pay AS totalPay,
tj_trade_total_kpi.total_pay_yd_db AS totalPayYdDb,
tj_trade_total_kpi.total_pay_pw_db AS totalPayPwDb,
tj_trade_total_kpi.visitor_num AS visitorNum,
tj_trade_total_kpi.visitor_num_yd_db AS visitorNumYdDb,
tj_trade_total_kpi.visitor_num_db AS visitorNumDb,
tj_trade_total_kpi.pv AS pv,
tj_trade_total_kpi.pv_yd_db AS pvYdDb,
tj_trade_total_kpi.pv_pw_db AS pvPwDb,
tj_trade_total_kpi.buyer_num AS BuyerNum,
tj_trade_total_kpi.buyer_num_yd_db AS BuyerNumYdDb,
tj_trade_total_kpi.buyer_num_pw_db AS BuyerNumPwDb,
tj_trade_total_kpi.order_num AS orderNum,
tj_trade_total_kpi.order_num_yd_db AS orderNumYdDb,
tj_trade_total_kpi.order_num_pw_db AS orderNumPwDb,
tj_trade_total_kpi.shop_num AS shopNum,
tj_trade_total_kpi.shop_num_yd_db AS shopNumYdDb,
tj_trade_total_kpi.shop_num_pw_db AS shopNumPwDb,
tj_trade_total_kpi.cvr AS cvr,
tj_trade_total_kpi.cvr_yd_db AS cvrYdDb,
tj_trade_total_kpi.cvr_pw_db AS cvrPwDb,
tj_trade_total_kpi.pct AS pct,
tj_trade_total_kpi.pct_yd_db AS cvrYdDb,
tj_trade_total_kpi.pct_yw_db AS cvrYwDb,
tj_trade_total_kpi.tkje AS tkje,
tj_trade_total_kpi.tkje_yd_db AS tkjeYdDb,
tj_trade_total_kpi.tkje_pw_db AS tkjePwDb
FROM
tj_trade_total_kpi WHERE 1=1
  <#if startDate?? && startDate?length gt 0>  AND tj_trade_total_kpi.date_created >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tj_trade_total_kpi.date_created <= '${endDate}' </#if>
 </#if>
 <#if purchseOrTrade == 'purchase'>
SELECT
tj_purchase_total_kpi.id,
tj_purchase_total_kpi.stat_date AS statDate,
tj_purchase_total_kpi.total_pay AS totalPay,
tj_purchase_total_kpi.total_pay_yd_db AS totalPayYdDb,
tj_purchase_total_kpi.total_pay_pw_db AS totalPayPwDb,
tj_purchase_total_kpi.visitor_num AS visitorNum,
tj_purchase_total_kpi.visitor_num_yd_db AS visitorNumYdDb,
tj_purchase_total_kpi.visitor_num_db AS visitorNumDb,
tj_purchase_total_kpi.pv AS pv,
tj_purchase_total_kpi.pv_yd_db AS pvYdDb,
tj_purchase_total_kpi.pv_pw_db AS pvPwDb,
tj_purchase_total_kpi.buyer_num AS BuyerNum,
tj_purchase_total_kpi.buyer_num_yd_db AS BuyerNumYdDb,
tj_purchase_total_kpi.buyer_num_pw_db AS BuyerNumPwDb,
tj_purchase_total_kpi.order_num AS orderNum,
tj_purchase_total_kpi.order_num_yd_db AS orderNumYdDb,
tj_purchase_total_kpi.order_num_pw_db AS orderNumPwDb,
tj_purchase_total_kpi.shop_num AS shopNum,
tj_purchase_total_kpi.shop_num_yd_db AS shopNumYdDb,
tj_purchase_total_kpi.shop_num_pw_db AS shopNumPwDb,
tj_purchase_total_kpi.cvr AS cvr,
tj_purchase_total_kpi.cvr_yd_db AS cvrYdDb,
tj_purchase_total_kpi.cvr_pw_db AS cvrPwDb,
tj_purchase_total_kpi.pct AS pct,
tj_purchase_total_kpi.pct_yd_db AS cvrYdDb,
tj_purchase_total_kpi.pct_yw_db AS cvrYwDb,
tj_purchase_total_kpi.tkje AS tkje,
tj_purchase_total_kpi.tkje_yd_db AS tkjeYdDb,
tj_purchase_total_kpi.tkje_pw_db AS tkjePwDb
FROM
tj_purchase_total_kpi WHERE 1=1
 <#if startDate?? && startDate?length gt 0>  AND tj_purchase_total_kpi.date_created >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tj_purchase_total_kpi.date_created <= '${endDate}' </#if>
  </#if>