package pub.makers.shop.thirdpart.vo;

import java.io.Serializable;

/**
 * Created by kok on 2017/6/7.
 */
public class CityVo implements Serializable {
    private String province; // 省份
    private String city; // 城市
    private String area; // 区

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
