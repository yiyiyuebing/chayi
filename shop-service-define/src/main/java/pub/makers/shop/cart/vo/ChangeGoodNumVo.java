package pub.makers.shop.cart.vo;

import java.io.Serializable;

/**
 * Created by kok on 2017/8/17.
 */
public class ChangeGoodNumVo implements Serializable {
    private Integer num;
    private String canAdd;
    private String canSub;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(String canAdd) {
        this.canAdd = canAdd;
    }

    public String getCanSub() {
        return canSub;
    }

    public void setCanSub(String canSub) {
        this.canSub = canSub;
    }
}
