package pub.makers.shop.store.vo;


import pub.makers.shop.store.entity.VtwoStorePhoto;
import pub.makers.shop.tradeGoods.entity.Image;

import java.io.Serializable;

/**
 * Created by dy on 2017/4/14.
 */
public class ImageVo implements Serializable {

    private String id;
    private String storeId;
    private String url;
    private String picUrl;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static ImageVo fromImage(Image image) {
        ImageVo vo = new ImageVo();
        vo.setId(image.getId().toString());
        vo.setUrl(image.getPicUrl());
        return vo;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public static ImageVo fromStorePhoto(VtwoStorePhoto vtwoStorePhoto) {
        ImageVo vo = new ImageVo();
        vo.setId(vtwoStorePhoto.getId());
        vo.setStoreId(vtwoStorePhoto.getStoreId().toString());
        vo.setUrl(vtwoStorePhoto.getPhotoUrl());
        return vo;
    }
}
