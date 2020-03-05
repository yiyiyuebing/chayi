package pub.makers.shop.tradeGoods.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by daiwenfa on 2017/5/28.
 */
public class GoodsEvaluationVo implements Serializable {
    private String id;              //主键

    private String goodSkuid;       //商品skuid

    private String user;              //用户id

    private String username;        //用户名称

    private String content;         //评论内容

    private String image;

    private Date evaluateTime;      //评价时间

    private Integer score;          //星级

    private String skuName;         //sku名称

    private String photo;           //图片

    private String indentId;        //订单id

    private String[] urlArray;      //图片url数组

    private String headImage;       //用户头像

    private String purGoodsId;
    private List<EvaluationImageVo> imageVoList;

    public List<EvaluationImageVo> getImageVoList() {
        return imageVoList;
    }

    public void setImageVoList(List<EvaluationImageVo> imageVoList) {
        this.imageVoList = imageVoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodSkuid() {
        return goodSkuid;
    }

    public void setGoodSkuid(String goodSkuid) {
        this.goodSkuid = goodSkuid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String[] getUrlArray() {
        return urlArray;
    }

    public void setUrlArray(String[] urlArray) {
        this.urlArray = urlArray;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getPurGoodsId() {
        return purGoodsId;
    }

    public void setPurGoodsId(String purGoodsId) {
        this.purGoodsId = purGoodsId;
    }
}
