select id, name, trade_head_store_id, s.shopId as subbranch_id, buyer_id, referrer_id, create_time,
		total_amount, payment_amount, pay_time, `number`, `type`, province, city, town, address,
		receiver_phone, buyer_remark, express_number, express_company, weight, carriage,
		shipper, ship_time, receiver, `status`, pay_type, pay_account, need_invoice, invoice_name,
		invoice_content, finish_time,  delete_flag, deal_status, refund_id, return_id,ticket_num,buyer_carriage,buy_type,ship_notice ,
		remark, refund_remark, return_remark, reject_refund, reject_return , order_type as orderType, 'trade' as orderBizType
		from indent i
		LEFT JOIN (SELECT
			store.ssid,
			ss.id as shopId
		FROM
			store_subbranch ss
		LEFT JOIN (
				SELECT
					sss.id AS ssid,
					CASE
				WHEN sss.is_sub_account = 'T' THEN
					sss.parent_subranch_id
				ELSE
					sss.id
				END AS sid
				FROM
					store_subbranch sss
			) store ON store.sid = ss.id
		) s ON s.ssid = i.subbranch_id
		where
		id=  ${orderId};
