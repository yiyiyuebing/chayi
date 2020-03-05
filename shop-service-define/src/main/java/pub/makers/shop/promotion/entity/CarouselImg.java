package pub.makers.shop.promotion.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kok on 2017/6/2.
 */
public class CarouselImg implements Serializable {
    private Long id;
    private Integer status;// 状态(0-有效,1-无效)
    private Integer lineStatus;// 链接类型(0-内联,1-外联)
    private Long category;// 关联主题
    private String richText;// 富文本
    private Integer sort;// 排序
    private String remk;// 描述
    private String picUrl;// 图片地址
    private Date updateTime;// 更新时间
    private Date createTime;// 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemk() {
        return remk;
    }

    public void setRemk(String remk) {
        this.remk = remk;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
}
