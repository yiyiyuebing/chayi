package pub.makers.shop.invoice.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pub.makers.base.exception.ValidateUtils;
import pub.makers.daotemplate.vo.Conds;
import pub.makers.daotemplate.vo.Update;
import pub.makers.shop.base.utils.IdGenerator;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.invoice.entity.Invoice;
import pub.makers.shop.invoice.vo.InvoiceVo;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/8/31.
 */
@Service(version = "1.0.0")
public class InvoiceBizServiceImpl implements InvoiceBizService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource(name = "purchaseInvoiceServiceImpl")
    private InvoiceService purchaseInvoiceService;
    @Resource(name = "tradeInvoiceServiceImpl")
    private InvoiceService tradeInvoiceService;

    @Override
    public void saveInvoice(InvoiceVo invoiceVo) {
        ValidateUtils.notNull(invoiceVo, "发票信息为空");
        ValidateUtils.notNull(invoiceVo.getUserId(), "用户id为空");
        ValidateUtils.notNull(invoiceVo.getName(), "发票抬头为空");
        if ("单位".equals(invoiceVo.getName())) {
            ValidateUtils.notNull(invoiceVo.getContent(), "发票内容为空");
        } else {
            return;
        }
        ValidateUtils.notNull(invoiceVo.getOrderBizType(), "业务类型为空");

        InvoiceService invoiceService = getService(invoiceVo.getOrderBizType());
        InvoiceVo vo = getInvoiceInfoByUser(invoiceVo.getOrderBizType(), invoiceVo.getUserId());
        if (StringUtils.isNotEmpty(vo.getId())) {
            invoiceService.update(Update.byId(vo.getId()).set("name", invoiceVo.getName()).set("content", invoiceVo.getContent()).set("duty_paragraph", invoiceVo.getDutyParagraph()).set("last_updated", new Date()));
        } else {
            Invoice invoice = invoiceVo.toInvoice();
            invoice.setId(IdGenerator.getDefault().nextId());
            invoice.setDateCreated(new Date());
            invoice.setLastUpdated(new Date());
            invoiceService.insert(invoice);
        }
    }

    @Override
    public InvoiceVo getInvoiceInfoByUser(OrderBizType orderBizType, String userId) {
        InvoiceService invoiceService = getService(orderBizType);
        List<Invoice> invoiceList = invoiceService.list(Conds.get().eq("user_id", userId).order("date_created desc, last_updated desc").page(0, 1));
        if (invoiceList.isEmpty()) {
            return new InvoiceVo();
        } else {
            return new InvoiceVo(invoiceList.get(0));
        }
    }

    private InvoiceService getService(OrderBizType orderBizType) {
        if (OrderBizType.trade.equals(orderBizType)) {
            return tradeInvoiceService;
        } else {
            return purchaseInvoiceService;
        }
    }
}
