package pub.makers.shop.store.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/4/15.
 */
public class SalesReturnReasonVo implements Serializable {

    private Long id;

    private String reason;

    private String value;

    private String rank;

    private Date updateTime;

    private Date createTime;

    private String copyId;//复制id ，怕长整形传输出错

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValue() {
        if (value == null) {
            return reason;
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
