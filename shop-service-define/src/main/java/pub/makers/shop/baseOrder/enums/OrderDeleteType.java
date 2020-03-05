package pub.makers.shop.baseOrder.enums;

/**
 * Created by kok on 2017/6/18.
 */
public enum OrderDeleteType {
    buyer("买家删除"), seller("卖家删除");

    OrderDeleteType(String name) {
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
