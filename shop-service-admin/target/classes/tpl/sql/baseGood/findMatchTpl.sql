select t.*
from good_page_tpl t
join good_page_tpl_apply ta on t.id = ta.tpl_id
where t.tpl_class_code = "${query.pageTplClassify}"
and t.order_biz_type = "${query.orderBizType}"
<#if query.goodSkuId?? && query.goodSkuId != "">
    and ta.good_ids like "%${query.goodSkuId}%"
<#elseif query.classifyId?? && query.classifyId != "">
    and ta.classify_ids like "%${query.classifyId}%"
</#if>
and t.is_valid = "T"
and t.del_flag = "F"
and ta.is_valid = "T"
and ta.del_flag = "F"
order by ta.sort asc