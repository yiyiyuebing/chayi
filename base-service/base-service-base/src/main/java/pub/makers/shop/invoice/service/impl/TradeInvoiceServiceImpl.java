package pub.makers.shop.invoice.service.impl;

import org.springframework.stereotype.Service;
import pub.makers.daotemplate.service.impl.BaseCRUDServiceImpl;
import pub.makers.shop.invoice.dao.TradeInvoiceDao;
import pub.makers.shop.invoice.entity.Invoice;
import pub.makers.shop.invoice.service.InvoiceService;

/**
 * Created by kok on 2017/9/4.
 */
@Service
public class TradeInvoiceServiceImpl extends BaseCRUDServiceImpl<Invoice, String, TradeInvoiceDao> implements InvoiceService {
}
