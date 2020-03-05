package pub.makers.shop.store.vo;

import pub.makers.shop.store.entity.StoreLevel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/6/1.
 */
public class StoreLevelVo implements Serializable {

    private String levelId;

    private String traId;

    private String name;

    private String statue;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String storePro;

    private Integer sort;


    public StoreLevelVo() {
    }


    public StoreLevelVo(StoreLevel storeLevel) {
        this.levelId = storeLevel.getLevelId() + "";
        this.traId = storeLevel.getTraId() + "";
        this.name = storeLevel.getName();
        this.statue = storeLevel.getStatue() + "";
        this.createTime = storeLevel.getCreateTime();
        this.createBy = storeLevel.getCreateBy() + "";
        this.updateTime = storeLevel.getUpdateTime();
        this.updateBy = storeLevel.getUpdateBy() + "";
        this.storePro = storeLevel.getStorePro();
        this.sort = storeLevel.getSort();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getTraId() {
        return traId;
    }

    public void setTraId(String traId) {
        this.traId = traId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getStorePro() {
        return storePro;
    }

    public void setStorePro(String storePro) {
        this.storePro = storePro;
    }
}
