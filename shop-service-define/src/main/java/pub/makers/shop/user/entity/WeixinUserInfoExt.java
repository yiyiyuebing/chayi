package pub.makers.shop.user.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by devpc on 2017/7/24.
 * <p/>
 * make in shangguo
 * <p/>
 * this is a label table by weixinuser
 */
public class WeixinUserInfoExt implements Serializable{

    private Long id;   //标签ID

    private Long weixinUserId;   //微信用户ID

    private String remark;  //备注

    private String label;   //标签

    private String delFlag;   //删除状态

    private String isValid;   //是否有效

    private Date dateCreated;  //创建时间

    private Date lastUpdated;   //更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeixinUserId() {
        return weixinUserId;
    }

    public void setWeixinUserId(Long weixinUserId) {
        this.weixinUserId = weixinUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
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
}
