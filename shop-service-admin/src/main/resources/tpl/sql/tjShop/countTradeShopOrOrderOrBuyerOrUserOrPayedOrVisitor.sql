
<#if purchseOrTrade == 'trade'>
<#if presellStatus == 'totalShop'>
SELECT
COUNT(sub.subbranch_id) FROM            <#-- 查询分店数 -->
(SELECT
idt.subbranch_id
FROM
indent idt
LEFT JOIN weixin_user_info wui ON idt.buyer_id=wui.ID
LEFT JOIN trade_order_list_payment tolp ON tolp.order_id=idt.id
WHERE 1=1 AND tolp.pay_status !='F' AND idt.`status` !=1 AND idt.`status` !=20
<#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}' </#if>
) sub
 </#if>

 <#if presellStatus == 'totalBuyer'>
 SELECT COUNT(*) FROM
(SELECT
COUNT(wui.ID)                  <#-- 买家数 -->
FROM
indent idt
LEFT JOIN weixin_user_info wui ON idt.buyer_id=wui.ID
LEFT JOIN trade_order_list_payment tolp ON tolp.order_id=idt.id
WHERE 1=1
AND tolp.pay_status !='F'
AND idt.`status` !=1
AND idt.`status` !=20
 <#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}'  </#if>
GROUP BY wui.ID) AS AA;
</#if>

<#if presellStatus == 'totalOrder'>
 SELECT
COUNT(idt.id)                      <#-- 支付订单数 -->
FROM
indent idt
LEFT JOIN weixin_user_info wui ON idt.buyer_id=wui.ID
LEFT JOIN trade_order_list_payment tolp ON tolp.order_id=idt.id
WHERE 1=1 AND tolp.pay_status !='F' AND idt.`status` !=1 AND idt.`status` !=20
  <#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}' </#if>
 </#if>

 <#if presellStatus == 'totalPayed'>
 SELECT
SUM(tolp.pay_amount) AS totalPay   <#-- 付款总额 -->
FROM
trade_order_list_payment tolp
LEFT JOIN indent por ON por.id=tolp.order_id
WHERE tolp.pay_status='T' AND por.status!='20'
 <#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}' </#if>
   </#if>

 <#if presellStatus == 'totalUser'>
SELECT
COUNT(ttar.uri) AS visitorNum          <#-- 浏览量 -->
FROM
tj_tradeshop_access_record ttar WHERE 1=1
<#if startDate?? && startDate?length gt 0>  AND ttar.stay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND ttar.stay_time <= '${endDate}' </#if>
   </#if>

<#if presellStatus == 'totalVisitor'>
SELECT
COUNT(users.visitorNum) AS visitorNum
FROM
(SELECT
ttar.user_id AS visitorNum           <#-- 访客 -->
FROM
tj_tradeshop_access_record ttar WHERE 1=1
  <#if startDate?? && startDate?length gt 0>  AND ttar.stay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND ttar.stay_time <= '${endDate}' </#if>
 GROUP BY ttar.user_id HAVING ttar.user_id>1) users
  </#if>

<#if presellStatus == 'tuiKuan'>
SELECT
SUM(indent.refund_amount)                     <#-- 退款金额 -->
FROM
indent WHERE 1=1 AND indent.`status`=8
  <#if startDate?? && startDate?length gt 0>  AND indent.finish_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND indent.finish_time <= '${endDate}' </#if>
  </#if>
  </#if>


  <#-- ----------------------------------------------------------分界线---------------------------------------------------------------------------------- -->

   <#if purchseOrTrade == 'purchase'>
<#if presellStatus == 'totalShop'>
SELECT
COUNT(sub.id) FROM              <#-- 查询分店数 -->
(SELECT
ssh.id
FROM
store_subbranch ssh
LEFT JOIN purchase_order por ON por.buyer_id=ssh.id
LEFT JOIN purchase_order_list_payment polp ON polp.order_id=por.id
WHERE 1=1 AND polp.pay_status !='F' AND por.`status` !=1 AND por.`status` !=20
<#if startDate?? && startDate?length gt 0>  AND polp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND polp.pay_time <= '${endDate}' </#if>
) sub
 </#if>

 <#if presellStatus == 'totalBuyer'>            <#-- 买家数 -->
 SELECT COUNT(*) FROM
(SELECT
COUNT(wui.ID)
FROM
purchase_order idt
LEFT JOIN store_subbranch wui ON idt.buyer_id=wui.ID
LEFT JOIN purchase_order_list_payment tolp ON tolp.order_id=idt.id
WHERE 1=1
AND tolp.pay_status !='F'
AND idt.`status` !=1
AND idt.`status` !=20
 <#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}'  </#if>
GROUP BY wui.ID) AS AA;
</#if>

<#if presellStatus == 'totalOrder'>
 SELECT
COUNT(idt.id)                      <#-- 支付订单数 -->
FROM
purchase_order idt
LEFT JOIN store_subbranch wui ON idt.buyer_id=wui.ID
LEFT JOIN purchase_order_list_payment tolp ON tolp.order_id=idt.id
WHERE 1=1 AND tolp.pay_status !='F' AND idt.`status` !=1 AND idt.`status` !=20
 <#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}' </#if>
 </#if>

 <#if presellStatus == 'totalPayed'>
 SELECT
SUM(tolp.pay_amount) AS totalPay        <#-- 付款总额 -->
FROM
purchase_order_list_payment tolp
LEFT JOIN purchase_order por ON por.id=tolp.order_id
WHERE tolp.pay_status='T' AND por.status!='20'
 <#if startDate?? && startDate?length gt 0>  AND tolp.pay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND tolp.pay_time <= '${endDate}' </#if>
   </#if>

 <#if presellStatus == 'totalUser'> <#--浏览次数--->
SELECT
COUNT(ttar.uri) AS visitorNum
FROM
tj_purchaseshop_access_record ttar WHERE 1=1
<#if startDate?? && startDate?length gt 0>  AND ttar.stay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND ttar.stay_time <= '${endDate}' </#if>
   </#if>

<#if presellStatus == 'totalVisitor'>     <#-- 访客 -->
SELECT
COUNT(users.visitorNum) AS visitorNum
FROM
(SELECT
ttar.user_id AS visitorNum
FROM
tj_purchaseshop_access_record ttar WHERE 1=1
 <#if startDate?? && startDate?length gt 0>  AND ttar.stay_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND ttar.stay_time <= '${endDate}' </#if>
 GROUP BY ttar.user_id HAVING ttar.user_id>1) users
  </#if>

<#if presellStatus == 'tuiKuan'><#-- 退款金额 -->
SELECT
SUM(purchase_order.refund_amount)
FROM
purchase_order WHERE 1=1 AND purchase_order.`status`=8
  <#if startDate?? && startDate?length gt 0>  AND purchase_order.finish_time >= '${startDate}' </#if>
 <#if endDate?? && endDate?length gt 0>  AND purchase_order.finish_time <= '${endDate}' </#if>
  </#if>

  </#if>