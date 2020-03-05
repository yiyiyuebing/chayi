select
		b.id,
		(select i.pic_url from Image i where i.id=logo) as logo,
		(select i.pic_url from Image i where i.id=pc_logo) as pc_logo,
		b.name,url, b.supplier_name, b.brand_recommendation,
		b.create_time,
		b.create_by,
		b.update_time, b.update_by
		from purchase_goods pg
		LEFT JOIN cargo c ON pg.cargo_id = c.id
		LEFT JOIN cargo_brand b ON b.id= c.brand_id
		WHERE  pg.status=1
		<#if classifyIds??>
			AND (
  	<#list classifyIds as classify>
    	<#if classify_index gt 0>
      	OR
    	</#if>
   		pg.group_id like "%${classify}%"
  	</#list>
  	)
		</#if>
		GROUP BY  b.id
		ORDER BY b.sort DESC