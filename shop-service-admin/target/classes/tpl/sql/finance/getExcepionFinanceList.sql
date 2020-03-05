select
    *
    from finance_accountspay
    where 1=1 and
     <#if orderType?? && orderType == "order">
     u8_order_id is NULL and statue = 0
     </#if>
     <#if orderType?? && orderType == "accept">
     u8_order_id is NULL and statue = 0
     </#if>
