SELECT
	psf.*
FROM
	purchase_shopping_favorite psf
LEFT JOIN purchase_goods_sku pgs on pgs.pur_goods_id = psf.goods_id
WHERE
	1 = 1
AND psf.del_flag != 'T'
AND pgs.del_flag != 'T'
<#if query.goodsId?? && query.goodsId != "">
AND psf.goods_id = ${query.goodsId}
</#if>
<#if query.userId?? && query.userId != "">
AND psf.user_id = ${query.userId}
</#if>
<#if query.shopId?? && query.shopId != "">
AND psf.shop_id = ${query.shopId}
</#if>
group by psf.id
order by psf.create_date DESC
