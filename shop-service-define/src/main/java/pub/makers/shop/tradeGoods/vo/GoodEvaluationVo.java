package pub.makers.shop.tradeGoods.vo;

import pub.makers.shop.base.utils.DateParseUtil;
import pub.makers.shop.cargo.entity.vo.ImageGroupVo;
import pub.makers.shop.tradeGoods.entity.GoodEvaluation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GoodEvaluationVo implements Serializable {
	private String id;              //主键

    private String goodId;

    private String goodSkuid;       //商品skuid

    private String user;              //用户id

    private String username;        //用户名称

    private String content;         //评论内容

    private String image;

    private String evaluateTime;      //评价时间

    private Integer score;          //星级
    
    private String goodName;         //sku名称
    
    private String photo;           //图片
    
    private String indentId;        //订单id

    private String[] urlArray;      //图片url数组

    private String headImage;       //用户头像

    private List<String> imageUrlList;

    private ImageGroupVo images;

    private Date buyTime;

    private GoodEvaluationVo replied;

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

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
        this.username = username == null ? null : username.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public ImageGroupVo getImages() {
        return images;
    }

    public void setImages(ImageGroupVo images) {
        this.images = images;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public GoodEvaluationVo getReplied() {
        return replied;
    }

    public void setReplied(GoodEvaluationVo replied) {
        this.replied = replied;
    }

    public static GoodEvaluationVo fromGoodEvaluation(GoodEvaluation goodEvaluation) {
        GoodEvaluationVo vo = new GoodEvaluationVo();
        vo.setId(goodEvaluation.getId().toString());
        vo.setGoodId(goodEvaluation.getGoodId());
        vo.setGoodSkuid(goodEvaluation.getGoodSkuId());
        vo.setUser(goodEvaluation.getUser());
        vo.setUsername(goodEvaluation.getUserName());
        vo.setContent(goodEvaluation.getContent());
        vo.setImage(goodEvaluation.getImage());
        vo.setEvaluateTime(DateParseUtil.formatDate(goodEvaluation.getEvaluateTime()));
        vo.setScore(goodEvaluation.getScore());
        vo.setIndentId(goodEvaluation.getOrderId());
        vo.setHeadImage(goodEvaluation.getHeadImgUrl());
        vo.setGoodName(goodEvaluation.getGoodName());
        return vo;
    }

    public GoodEvaluation toGoodEvaluation() {
        GoodEvaluation evaluation = new GoodEvaluation();
        evaluation.setGoodSkuId(this.goodSkuid);
        evaluation.setUserName(this.user);
        evaluation.setUserName(this.username);
        evaluation.setContent(this.content);
        evaluation.setImage(this.image);
        evaluation.setScore(this.score);
        evaluation.setOrderId(this.indentId);
        return evaluation;
    }
}