package pub.makers.shop.index.pojo;

import pub.makers.shop.index.enums.IndexAdPlatform;
import pub.makers.shop.index.enums.IndexAdPost;

import java.io.Serializable;

/**
 * Created by kok on 2017/7/22.
 */
public class IndexAdImagesQuery implements Serializable {
    private IndexAdPost post;
    private IndexAdPlatform platform;
    private String classifyId;
    private Integer limit = 5;

    public IndexAdImagesQuery() {
    }

    public IndexAdImagesQuery(IndexAdPost post, IndexAdPlatform platform, String classifyId, Integer limit) {
        this.post = post;
        this.platform = platform;
        this.classifyId = classifyId;
        this.limit = limit;
    }

    public IndexAdPost getPost() {
        return post;
    }

    public void setPost(IndexAdPost post) {
        this.post = post;
    }

    public IndexAdPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(IndexAdPlatform platform) {
        this.platform = platform;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
