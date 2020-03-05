package pub.makers.shop.bill.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/9/8.
 */
public class MqTaskInfo implements Serializable {

    private static final long serialVersionUID = 7750451681291563434L;

    private Long id;
    private String msgType;
    private String msgContent;
    private Date sendTime;
    private String status;
    private Date finishTime;
    private Long spendTime;
    private String backMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    public String getBackMsg() {
        return backMsg;
    }

    public void setBackMsg(String backMsg) {
        this.backMsg = backMsg;
    }
}
