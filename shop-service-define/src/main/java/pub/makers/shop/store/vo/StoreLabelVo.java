package pub.makers.shop.store.vo;

import pub.makers.shop.store.entity.StoreLabel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2017/10/9.
 */
public class StoreLabelVo implements Serializable {

    private String id;
    private String name;
    private Boolean isChecked;
    private String storeId;
    private Date dateCreated;
    private Date dateUpdate;
    private Integer fansNum;
    private String newFansIds;
    private String delFansIds;
    private String shopIds;
    private String searchKeyword;
    private String selFansId;

    public StoreLabelVo() {
    }

    public StoreLabelVo(StoreLabel storeLabel) {
        this.id = storeLabel.getId() + "";
        this.name = storeLabel.getName();
        this.isChecked = false;
        this.storeId = storeLabel.getStoreId() + "";
        this.dateCreated = storeLabel.getDateCreated();
        this.dateUpdate = storeLabel.getDateUpdate();
    }

    public String getSelFansId() {
        return selFansId;
    }

    public void setSelFansId(String selFansId) {
        this.selFansId = selFansId;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getShopIds() {
        return shopIds;
    }

    public void setShopIds(String shopIds) {
        this.shopIds = shopIds;
    }

    public String getNewFansIds() {
        return newFansIds;
    }

    public void setNewFansIds(String newFansIds) {
        this.newFansIds = newFansIds;
    }

    public String getDelFansIds() {
        return delFansIds;
    }

    public void setDelFansIds(String delFansIds) {
        this.delFansIds = delFansIds;
    }

    public Integer getFansNum() {
        return fansNum;
    }

    public void setFansNum(Integer fansNum) {
        this.fansNum = fansNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
