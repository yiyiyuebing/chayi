<#if isChecked?? && isChecked!="">
  select * from (
      SELECT pc.id,pc. NAME,pc.img_url AS smallImage,"T" as isRelevance
      FROM purchase_classify pc
      WHERE pc.id = '${goodIds}'
    UNION
      (
      SELECT pc.id,pc. NAME,pc.img_url AS smallImage,"" as isRelevance
      FROM purchase_classify pc
      WHERE 1 = 1 and pc.id != '${goodIds}'
      order by pc.parent_id
      )
    ) p
  where 1=1
	<#if relevanceName?? && relevanceName!="">
    and p.name like '%${relevanceName}%'
  </#if>
<#else>
  SELECT * FROM (
      SELECT pgs.id,pg.pur_goods_name AS NAME,cs.retail_price,
        ( SELECT i.pic_url FROM image i WHERE i.group_id = c.pc_album_id LIMIT 1) AS smallImage,
        pgs.pur_goods_id AS goodsId,
        cs. CODE,
        "T" as isRelevance
      FROM
        purchase_goods_sku pgs
      LEFT JOIN cargo_sku cs ON cs.id = pgs.cargo_sku_id
      LEFT JOIN cargo c ON cs.cargo_id = c.id
      LEFT JOIN purchase_goods pg ON pg.id = pgs.pur_goods_id
      WHERE
        1 = 1
      AND pgs.`status` = '1'
      AND pgs.id = '${goodIds}'
    UNION
      (
        SELECT pgs.id,pg.pur_goods_name AS NAME,cs.retail_price,
          (SELECT i.pic_url FROM image i WHERE i.group_id = c.pc_album_id LIMIT 1 ) AS smallImage,
          pgs.pur_goods_id AS goodsId,
          cs. CODE,
          "" as isRelevance
        FROM
          purchase_goods_sku pgs
        LEFT JOIN cargo_sku cs ON cs.id = pgs.cargo_sku_id
        LEFT JOIN cargo c ON cs.cargo_id = c.id
        LEFT JOIN purchase_goods pg ON pg.id = pgs.pur_goods_id
        WHERE
          1 = 1
        AND pgs.`status` = '1'
        AND pgs.id != '${goodIds}'
        ORDER BY pgs.order_index DESC
      )
    ) p
  WHERE 1 = 1
  <#if relevanceName?? && relevanceName!="">
    AND (p. NAME LIKE '%${relevanceName}%' OR p.CODE LIKE '%${relevanceName}%')
  </#if>
  <#if startPrice?? && startPrice!=0>
    AND p.retail_price >= ${startPrice}
  </#if>
  <#if endPrice?? && endPrice!=0>
    AND p.retail_price <= ${endPrice}
  </#if>
</#if>
