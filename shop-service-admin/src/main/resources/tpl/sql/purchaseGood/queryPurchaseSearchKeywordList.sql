select * from purchase_search_keyword psk where psk.del_flag = 'F'
<#if keyword?? && keyword!="">
  and psk.keyword like '%${keyword}%'
</#if>
<#if platform?? && platform !="">
  and psk.platform = '${platform}'
</#if>
 order by psk.sort desc,psk.date_created desc