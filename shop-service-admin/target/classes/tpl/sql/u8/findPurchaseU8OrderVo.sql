select o.id, order_no as name,  trade_head_store_id , ifnull(subbranch_id, buyer_id) as subbranch_id  , buyer_id, '' as referrer_id,o.create_time,
		total_amount, payment_amount, pay_time, o.number, `type`, o.province, o.city, district as  town,o.address,
		receiver_phone, buyer_remark, express_number, express_company, weight, carriage,
		shipper, ship_time, receiver, `status`, pay_type, pay_account, need_invoice, invoice_name,
		invoice_content, finish_time,  delete_flag, deal_status, refund_id, return_id,express_number as ticket_num, buyer_carriage,'' as buy_type,ship_notice ,
		remark, refund_remark, return_remark, reject_refund, reject_return , order_type as orderType, 'purchase' as orderBizType
		 from purchase_order  o  WHERE o.id= ${orderId};