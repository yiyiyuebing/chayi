package pub.makers.shop.tradeGoods.entity;

import java.io.Serializable;
import java.util.Date;

public class GoodEvaluation implements Serializable{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    private String goodId;

    /** SKU id */
    private String goodSkuId;

    /** 用户 */
    private String user;

    /** 用户名 */
    private String userName;

    /** 评价内容 */
    private String content;

    /** 图片评论 */
    private String image;

    /** 评价时间*/
    private Date evaluateTime;

    /** 分数 */
    private Integer score;

    /*订单号*/
    private String orderId;

    /*商品名称*/
    private String goodName;

    /*商品类型*/
    private String goodType;

    /*是否隐藏*/
    private String isHide;

    /*是否回复*/
    private String isReplied;

    /*回复id*/
    private String repliedId;

    private String headImgUrl;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public String getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(String goodSkuId) {
        this.goodSkuId = goodSkuId;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getUser(){
        return user;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setEvaluateTime(Date evaluateTime){
        this.evaluateTime = evaluateTime;
    }

    public Date getEvaluateTime(){
        return evaluateTime;
    }

    public void setScore(Integer score){
        this.score = score;
    }

    public Integer getScore(){
        return score;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodType() {
        return goodType;
    }

    public void setGoodType(String goodType) {
        this.goodType = goodType;
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

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
