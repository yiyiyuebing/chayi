package pub.makers.shop.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Think on 2017/8/25.
 */
public class Opinion implements Serializable {

    private Long id;

    private Long clientId;

    private String versionName;

    private String type;

    private Date createTime;

    private String description;

    private String clientName;

    private String clientPhone;

    private Integer platform;





    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Integer getPlatform() {
        return platform;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}
