SELECT
	cargo.id AS cargoId,
	cargo.`name`,
	cargo.pc_album_id as pcAlbumId
FROM
	cargo
where 1=1 and cargo.del_flag != 'T'
<#if cargoName??>
and cargo.name like '%${cargoName}%'
</#if>
<#if classifyId??>
and cargo.classify_id = '${classifyId}'
</#if>
<#if isChecked??>
and cargo.id in (${cargoRelevanceId})
</#if>
order by cargo.create_time desc LIMIT ?,?