package pub.makers.shop.finance.vo;

import java.math.BigDecimal;

/**
 * Created by dy on 2017/4/14.
 */
public class FinanceAccountspayItemVo {
    private String id;

    private String mainid;

    private Integer type;

    private String customerid;

    private String customercode;

    private String customername;

    private String departmentcode;

    private BigDecimal amount;

    private BigDecimal originalamount;

    private String itemcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode == null ? null : customercode.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode == null ? null : departmentcode.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOriginalamount() {
        return originalamount;
    }

    public void setOriginalamount(BigDecimal originalamount) {
        this.originalamount = originalamount;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode == null ? null : itemcode.trim();
    }
}
