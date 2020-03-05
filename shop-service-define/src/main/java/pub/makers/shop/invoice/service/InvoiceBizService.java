package pub.makers.shop.invoice.service;

import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.invoice.vo.InvoiceVo;

/**
 * Created by dy on 2017/8/31.
 */
public interface InvoiceBizService {
    /**
     * 保存发票信息
     */
    void saveInvoice(InvoiceVo invoiceVo);

    /**
     * 通过用户ID获取发票记录
     * @param orderBizType
     * @param userId
     * @return
     */
    InvoiceVo getInvoiceInfoByUser(OrderBizType orderBizType, String userId);

}
