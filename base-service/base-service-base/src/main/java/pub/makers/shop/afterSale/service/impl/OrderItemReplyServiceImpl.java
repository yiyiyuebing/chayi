package pub.makers.shop.afterSale.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.afterSale.dao.OrderItemReplyDao;
import pub.makers.shop.afterSale.entity.OrderItemReply;
import pub.makers.shop.afterSale.service.OrderItemReplyService;

/**
 * Created by dy on 2017/5/17.
 */
@Service
public class OrderItemReplyServiceImpl extends BaseCRUDServiceImpl<OrderItemReply, String, OrderItemReplyDao> implements OrderItemReplyService {

}
