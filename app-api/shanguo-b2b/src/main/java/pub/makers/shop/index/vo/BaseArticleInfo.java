package pub.makers.shop.index.vo;

import pub.makers.shop.index.entity.IndexAdImages;
import pub.makers.shop.marketing.entity.Toutiao;
import pub.makers.shop.marketing.vo.OnlineStudyVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dy on 2017/6/5.
 */
public class BaseArticleInfo implements Serializable {

    private String id;
    private String title;
    private String type;

    private Long totalRecods;
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;

    private String articleType;

    List<BaseArticleInfo> baseArticleInfos;

    private IndexAdImages indexAdImages;

    public BaseArticleInfo() {
    }

    public BaseArticleInfo(OnlineStudyVo onlineStudyVo) {
        this.id = onlineStudyVo.getID();
        this.title = onlineStudyVo.getTitle();
        this.type = "article";
    }

    public BaseArticleInfo(Toutiao toutiao) {

        this.title = toutiao.getTitle();
        if ("sp".equals(toutiao.getClassify())) {
            this.id = toutiao.getUrl();
            this.type = "goods";
        } else if ("tw".equals(toutiao.getClassify())) {
            this.id = toutiao.getToutiaoId();
            this.type = "gonggaotw";
        } else {
            this.id = toutiao.getUrl();
            this.type = "article";
        }
    }


    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public IndexAdImages getIndexAdImages() {
        return indexAdImages;
    }

    public void setIndexAdImages(IndexAdImages indexAdImages) {
        this.indexAdImages = indexAdImages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTotalRecods() {
        return totalRecods;
    }

    public void setTotalRecods(Long totalRecods) {
        this.totalRecods = totalRecods;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        if (this.totalRecods != null) {
            return this.totalRecods.intValue() % this.pageSize > 0 ?  (this.totalRecods.intValue() / this.pageSize) + 1 :  this.totalRecods.intValue() / this.pageSize;
        }
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BaseArticleInfo> getBaseArticleInfos() {
        return baseArticleInfos;
    }

    public void setBaseArticleInfos(List<BaseArticleInfo> baseArticleInfos) {
        this.baseArticleInfos = baseArticleInfos;
    }
}
