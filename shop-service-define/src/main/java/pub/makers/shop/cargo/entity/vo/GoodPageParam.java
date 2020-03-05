package pub.makers.shop.cargo.entity.vo;

import java.io.Serializable;

/**
 * Created by daiwenfa on 2017/6/7.
 */
public class GoodPageParam implements Serializable {
    private String tplName;
    private String applyScope;
    private String isValid;
    private String sort;
    private String tplClassCode;

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
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

    public String getTplClassCode() {
        return tplClassCode;
    }

    public void setTplClassCode(String tplClassCode) {
        this.tplClassCode = tplClassCode;
    }
}
