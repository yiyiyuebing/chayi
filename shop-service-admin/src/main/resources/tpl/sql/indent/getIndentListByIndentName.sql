SELECT
	i.name as indentName, sum(il.number) as number, il.supply_price, il.gift_flag
FROM
	indent_list il
join indent i on i.id = il.indent_id
where 1=1
and i.name in (${indentNameList})
GROUP BY i.id, il.trade_good_sku_id
;