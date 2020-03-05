package pub.makers.shop.afterSale.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.afterSale.dao.OrderItemReplyImgDao;
import pub.makers.shop.afterSale.entity.OrderItemReplyImg;
import pub.makers.shop.afterSale.service.OrderItemReplyImgService;

/**
 * Created by dy on 2017/5/16.
 */
@Service
public class OrderItemReplyImgServiceImpl extends BaseCRUDServiceImpl<OrderItemReplyImg, String, OrderItemReplyImgDao> implements OrderItemReplyImgService {
}
