package pub.makers.shop.user.vo;

import java.io.Serializable;

/**
 * Created by daiwenfa on 2017/7/21.
 */
public class UserHomeStoreVo implements Serializable {
    private String id;
    private String homeStoreName;
    private String buyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeStoreName() {
        return homeStoreName;
    }

    public void setHomeStoreName(String homeStoreName) {
        this.homeStoreName = homeStoreName;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
}
