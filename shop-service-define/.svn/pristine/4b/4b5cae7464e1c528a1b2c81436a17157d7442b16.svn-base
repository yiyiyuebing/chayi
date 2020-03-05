package pub.makers.shop.marketing.vo;

import pub.makers.shop.marketing.entity.OnlineStudy;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/5/3.
 */
public class OnlineStudyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ID;

    private String title;

    private String studyType;

    private String studyChildType;

    private String studyChildTypeName;

    private Long type;

    private Long readNum;

    private String author;

    private String videoUrl;

    private String content;

    private String covePic;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private String file;

    private String covePicUrl;

    private String label;

    private Integer videoShow;

    private String studyTypeName;

    private String linkGoods;

    private String studyTag;

    private Integer isShare;

    private String order;   //排序字段

    private String orderType;   //排序类型（desc, asc）

    private Integer isLike;

    private Integer isZan;

    private String material;


    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public Integer getIsZan() {
        return isZan;
    }

    public void setIsZan(Integer isZan) {
        this.isZan = isZan;
    }

    public String getCovePicUrl() {
        return covePicUrl;
    }

    public void setCovePicUrl(String covePicUrl) {
        this.covePicUrl = covePicUrl;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }

    public String getStudyChildTypeName() {
        return studyChildTypeName;
    }

    public void setStudyChildTypeName(String studyChildTypeName) {
        this.studyChildTypeName = studyChildTypeName;
    }

    public String getStudyTag() {
        return studyTag;
    }

    public void setStudyTag(String studyTag) {
        this.studyTag = studyTag;
    }

    public String getLinkGoods() {
        return linkGoods;
    }

    public void setLinkGoods(String linkGoods) {
        this.linkGoods = linkGoods;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getStudyChildType() {
        return studyChildType;
    }

    public void setStudyChildType(String studyChildType) {
        this.studyChildType = studyChildType;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCovePic() {
        return covePic;
    }

    public void setCovePic(String covePic) {
        this.covePic = covePic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getVideoShow() {
        return videoShow;
    }

    public void setVideoShow(Integer videoShow) {
        this.videoShow = videoShow;
    }

    public String getStudyTypeName() {
        return studyTypeName;
    }

    public void setStudyTypeName(String studyTypeName) {
        this.studyTypeName = studyTypeName;
    }

    public static OnlineStudyVo simpleFromOnlineStudy(OnlineStudy study) {
        OnlineStudyVo vo = new OnlineStudyVo();
        vo.setID(study.getID().toString());
        vo.setTitle(study.getTitle());
        return vo;
    }

    public static OnlineStudyVo fromOnlineStudy(OnlineStudy study) {
        OnlineStudyVo vo = new OnlineStudyVo();
        vo.setID(study.getID().toString());
        vo.setTitle(study.getTitle());
        vo.setStudyType(study.getStudyType() == null ? null : study.getStudyType().toString());
        vo.setStudyChildType(study.getStudyChildType());
        vo.setType(study.getType());
        vo.setReadNum(study.getReadNum());
        vo.setAuthor(study.getAuthor());
        vo.setVideoUrl(study.getVideoUrl());
        vo.setContent(study.getContent());
        vo.setCovePic(study.getCovePic());
        vo.setCreateTime(study.getCreateTime());
        vo.setCreateBy(study.getCreateBy());
        vo.setUpdateTime(study.getUpdateTime());
        vo.setUpdateBy(study.getUpdateBy());
        vo.setFile(study.getFile());
        vo.setLabel(study.getLabel());
        vo.setVideoShow(study.getVideoShow());
        vo.setStudyTypeName(study.getStudyTypeName());
        vo.setStudyTag(study.getStudyTag());
        vo.setIsShare(study.getIsShare());
        return vo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
