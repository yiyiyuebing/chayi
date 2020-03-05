select
    *
    from finance_accountspay
    where 1=1 and statue = 0
     <#if orderType?? && orderType == "order">
     and u8_order_id is NULL
     <#else>
     and u8_accounts_id is NULL
     </#if>
