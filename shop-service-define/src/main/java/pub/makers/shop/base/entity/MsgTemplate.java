package pub.makers.shop.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by dy on 2017/5/15.
 */
public class MsgTemplate implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id; //主键id

    private String templateKey; //模板关键词

    private String title; //内容标题

    private String content; //短信模板内容

    private String description; //模板使用说明

    private String isValid; //禁用状态 (0:禁用 ， 1:启用)

    private String type; //类型(trade:商城，purchase:采购)

    private Timestamp createTime; //创建时间

    private Timestamp updateTime; //更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateKey() {
        return templateKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
