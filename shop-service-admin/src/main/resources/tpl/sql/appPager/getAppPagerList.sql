SELECT
ap.last_updated AS lastUpdated,
ap.is_valid AS isValid,
ap.title AS title,
ap.id AS id,
ap.sort,
apmg.goods_image AS imageUrl,
apm1.title AS subTitle
FROM
app_pager ap
LEFT JOIN (SELECT app_pager_module.id,app_pager_module.page_id FROM app_pager_module WHERE app_pager_module.type='goods') apm ON ap.id=apm.page_id
LEFT JOIN (SELECT app_pager_module_good.goods_image,app_pager_module_good.module_id FROM app_pager_module_good ) apmg ON apm.id=apmg.module_id
LEFT JOIN (SELECT * FROM app_pager_module WHERE app_pager_module.type='title') apm1 ON apm1.page_id=ap.id
WHERE 1=1 AND ap.del_flag='F'
 <#if mainTitle?? && mainTitle != "">
   AND ap.title LIKE  "%${mainTitle}%"
  </#if>
   <#if subTitle?? && subTitle != "">
   AND apm1.title LIKE  "%${subTitle}%"
   </#if>
   <#if orderBizType?? && orderBizType != "">
   AND ap.order_biz_type= "${orderBizType}"
  </#if>
   GROUP BY ap.id
   ORDER BY ap.sort DESC
