package pub.makers.shop.store.entity;

import java.util.Date;

/**
 * Created by wby on 2016/11/22.
 * 店内照片
 */
public class VtwoStorePhoto {
    private Long id;
    private Long storeId;
    private String strStoreId;
    private String photoUrl;
    private Date createTime;
    private String isAble;
    private String storeName;
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    
    public String getStrStoreId() {
		return strStoreId;
	}

	public void setStrStoreId(String strStoreId) {
		this.strStoreId = strStoreId;
	}

	public String getId() {
        return String.valueOf(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsAble() {
        return isAble;
    }

    public void setIsAble(String isAble) {
        this.isAble = isAble;
    }



}
