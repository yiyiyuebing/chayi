package pub.makers.shop.user.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by daiwenfa on 2017/7/19.
 */
public class UserListTradeParam  implements Serializable {
    private String id;
    private String startDate;//时间筛选开始时间
    private String endDate;//时间筛选结束时间
    private String nickName;//昵称
    private String labelName;//标签名称
    private Double tranAmountStr;//交易额开始
    private Double tranAmountEnd;//交易额结束
    private Integer tranNumStr;//交易数开始
    private Integer tranNumEnd;//交易数结束
    private String lastTranDateStr;//上次交易时间开始
    private String lastTranDateEnd;//上次交易时间结束
    private Integer buyNumStr;//购买件数开始
    private Integer buyNumEnd;//购买件数结束
    private String province;//省
    private String city;//城市
    private String area;//地区
    private Integer buybackTime;//回购周期（天）

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLastTranDateStr() {
        return lastTranDateStr;
    }

    public void setLastTranDateStr(String lastTranDateStr) {
        this.lastTranDateStr = lastTranDateStr;
    }

    public String getLastTranDateEnd() {
        return lastTranDateEnd;
    }

    public void setLastTranDateEnd(String lastTranDateEnd) {
        this.lastTranDateEnd = lastTranDateEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }


    public Integer getTranNumStr() {
        return tranNumStr;
    }

    public void setTranNumStr(Integer tranNumStr) {
        this.tranNumStr = tranNumStr;
    }

    public Integer getTranNumEnd() {
        return tranNumEnd;
    }

    public void setTranNumEnd(Integer tranNumEnd) {
        this.tranNumEnd = tranNumEnd;
    }

    public Integer getBuyNumStr() {
        return buyNumStr;
    }

    public void setBuyNumStr(Integer buyNumStr) {
        this.buyNumStr = buyNumStr;
    }

    public Integer getBuyNumEnd() {
        return buyNumEnd;
    }

    public void setBuyNumEnd(Integer buyNumEnd) {
        this.buyNumEnd = buyNumEnd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

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

    public Integer getBuybackTime() {
        return buybackTime;
    }

    public void setBuybackTime(Integer buybackTime) {
        this.buybackTime = buybackTime;
    }

    public Double getTranAmountStr() {
        return tranAmountStr;
    }

    public void setTranAmountStr(Double tranAmountStr) {
        this.tranAmountStr = tranAmountStr;
    }

    public Double getTranAmountEnd() {
        return tranAmountEnd;
    }

    public void setTranAmountEnd(Double tranAmountEnd) {
        this.tranAmountEnd = tranAmountEnd;
    }
}
