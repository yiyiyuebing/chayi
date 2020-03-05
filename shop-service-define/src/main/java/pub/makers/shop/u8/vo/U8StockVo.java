package pub.makers.shop.u8.vo;

import pub.makers.shop.tradeOrder.entity.Indent;

import java.io.Serializable;

/**
 * Created by dy on 2017/6/30.
 */
public class U8StockVo implements Serializable {

    private String cinvcode;//U8返回货品编号
    private Double iquantity;//U8返回货品库存

    private Long id;//cargo_sku_stock表id
    private Long cargo_sku_id;
    private String code;//本地货品编号
    private Double out_shelves_no;//未上架数量
    private Double on_sales_no;//已上架未售
    private Double on_pay_no;//已售未付款
    private Double on_send_no;//已售未发货
    private Double remain_count;//留存
    private Integer type;//类型 0销售 1进货
    private Integer currStock;

    public Integer getCurrStock() {
        return currStock;
    }

    public void setCurrStock(Integer currStock) {
        this.currStock = currStock;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public void setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
    }

    public Double getIquantity() {
        return iquantity;
    }

    public void setIquantity(Double iquantity) {
        this.iquantity = iquantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCargo_sku_id() {
        return cargo_sku_id;
    }

    public void setCargo_sku_id(Long cargo_sku_id) {
        this.cargo_sku_id = cargo_sku_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getOut_shelves_no() {
        return out_shelves_no;
    }

    public void setOut_shelves_no(Double out_shelves_no) {
        this.out_shelves_no = out_shelves_no;
    }

    public Double getOn_sales_no() {
        return on_sales_no;
    }

    public void setOn_sales_no(Double on_sales_no) {
        this.on_sales_no = on_sales_no;
    }

    public Double getOn_pay_no() {
        return on_pay_no;
    }

    public void setOn_pay_no(Double on_pay_no) {
        this.on_pay_no = on_pay_no;
    }

    public Double getOn_send_no() {
        return on_send_no;
    }

    public void setOn_send_no(Double on_send_no) {
        this.on_send_no = on_send_no;
    }

    public Double getRemain_count() {
        return remain_count;
    }

    public void setRemain_count(Double remain_count) {
        this.remain_count = remain_count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
