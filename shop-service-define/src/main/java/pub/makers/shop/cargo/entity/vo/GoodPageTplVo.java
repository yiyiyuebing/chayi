package pub.makers.shop.cargo.entity.vo;

import java.io.Serializable;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public class GoodPageTplVo implements Serializable {
    private String id;
    private String tplName;
    private String modelName;
    private String applyScope;
    private String isValid;//  T启用
    private String sort;
    private String dateCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getApplyScope() {
        return applyScope;
    }

    public void setApplyScope(String applyScope) {
        this.applyScope = applyScope;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
