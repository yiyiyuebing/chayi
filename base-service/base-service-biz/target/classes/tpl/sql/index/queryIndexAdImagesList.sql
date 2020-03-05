select i.id,i.img_name as name,i.image_url as imageUrl,i.sort,i.is_valid as isValid,i.last_updated as lastUpdated from index_ad_images i
where 1=1
  and i.del_flag = 'F'
<#if postCode?? && postCode!="">
  and i.post_code = '${postCode}'
</#if>
order by i.date_created desc limit ?,?