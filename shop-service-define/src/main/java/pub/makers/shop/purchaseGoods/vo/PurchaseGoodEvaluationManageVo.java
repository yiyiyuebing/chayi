package pub.makers.shop.purchaseGoods.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2017/5/10.
 */
public class PurchaseGoodEvaluationManageVo implements Serializable{

    private String id;              //评论id

    private String purGoodsId;       //采购货物id

    private String content;         //评论内容

    private Integer score;          //评分

    private String goodName;        //商品名称

    private String skuName;         //商品SKU编码

    private String orderId;         //订单号

    private String userName;        //买家昵称

    private Date evaluateTime;      //评价时间

    private String isHide;         //是否隐藏

    private String isReplied;      //是否回复

    private String repliedId;       //回复id

    private String imageId;        //图片ID

    private List<PurchaseGoodEvaluationManageVo> replyEvaluationList;    //回复内容

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPurGoodsId() {
        return purGoodsId;
    }

    public void setPurGoodsId(String purGoodsId) {
        this.purGoodsId = purGoodsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }

    public String getIsReplied() {
        return isReplied;
    }

    public void setIsReplied(String isReplied) {
        this.isReplied = isReplied;
    }

    public String getRepliedId() {
        return repliedId;
    }

    public void setRepliedId(String repliedId) {
        this.repliedId = repliedId;
    }

    public List<PurchaseGoodEvaluationManageVo> getReplyEvaluationList() {
        return replyEvaluationList;
    }

    public void setReplyEvaluationList(List<PurchaseGoodEvaluationManageVo> replyEvaluationList) {
        this.replyEvaluationList = replyEvaluationList;
    }
}
