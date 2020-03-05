select count(*) from index_ad_images i where 1=1  and i.del_flag = 'F'
<#if postCode?? && postCode!="">
  and i.post_code = '${postCode}'
</#if>
order by i.date_created desc