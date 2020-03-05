package pub.makers.shop.invoice.vo;

import org.apache.commons.lang.StringUtils;
import pub.makers.shop.baseOrder.enums.OrderBizType;
import pub.makers.shop.invoice.entity.Invoice;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/8/31.
 */
public class InvoiceVo implements Serializable {

    private String id;
    private String userId;
    private String name;
    private String content;
    private String dutyParagraph;
    private Date dateCreated;
    private Date lastUpdated;
    private OrderBizType orderBizType;

    public InvoiceVo() {
    }

    public InvoiceVo(Invoice invoice) {
        this.id = invoice.getId() == null ? null : invoice.getId().toString();
        this.userId = invoice.getUserId() == null ? null : invoice.getUserId().toString();
        this.name = invoice.getName();
        this.content = invoice.getContent();
        this.dutyParagraph = invoice.getDutyParagraph();
        this.dateCreated = invoice.getDateCreated();
        this.lastUpdated = invoice.getLastUpdated();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public OrderBizType getOrderBizType() {
        return orderBizType;
    }

    public void setOrderBizType(OrderBizType orderBizType) {
        this.orderBizType = orderBizType;
    }

    public Invoice toInvoice() {
        Invoice invoice = new Invoice();
        invoice.setId(StringUtils.isEmpty(this.id) ? null : Long.valueOf(this.id));
        invoice.setUserId(StringUtils.isEmpty(this.userId) ? null : Long.valueOf(this.userId));
        invoice.setName(this.name);
        invoice.setContent(this.content);
        invoice.setDutyParagraph(this.dutyParagraph);
        invoice.setDateCreated(this.dateCreated);
        invoice.setLastUpdated(this.lastUpdated);
        return invoice;
    }
}
