select ifnull(ifnull(
  	(SELECT carriage_full from carriage_rule_detail 
	where indent_money_full <= ? AND INSTR(CONCAT(',',deliver_region,','),CONCAT(',',?,',')) > 0 
	AND carriage_rule_id = ? ORDER BY indent_money_full DESC limit 0,1),
	(SELECT carriage_not_full from carriage_rule_detail 
	where indent_money_full > ? AND INSTR(CONCAT(',',deliver_region,','),CONCAT(',',?,',')) > 0 
	AND carriage_rule_id = ? ORDER BY indent_money_full limit 0,1)),
	(SELECT carriage from carriage_rule where id = ?)) 
	from dual