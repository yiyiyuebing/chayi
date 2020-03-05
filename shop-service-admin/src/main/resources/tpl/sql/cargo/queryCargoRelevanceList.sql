SELECT
	cargo.id AS cargoId,
	cargo.`name`,
  (select i.pic_url from image i where i.group_id = cargo.pc_album_id limit 1) as smallImage
FROM
	cargo
where 1=1 and cargo.del_flag != 'T'
<#if cargoName??>
and (cargo.name like '%${cargoName}%' or cargo.cargo_no = '${cargoName}')
</#if>
<#if classifyId??>
and (${classifyId})
</#if>
<#if isChecked??>
and cargo.id in (${cargoRelevanceId})
</#if>
order by cargo.create_time desc LIMIT ?,?