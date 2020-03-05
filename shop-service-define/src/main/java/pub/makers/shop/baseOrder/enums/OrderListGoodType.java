package pub.makers.shop.baseOrder.enums;

/**
 * Created by kok on 2017/8/25.
 */
public enum  OrderListGoodType {
    normal("普通商品"), zengpin("赠品");

    OrderListGoodType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
