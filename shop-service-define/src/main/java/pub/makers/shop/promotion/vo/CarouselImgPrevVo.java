package pub.makers.shop.promotion.vo;

/**
 * H5前段显示对象信息
 * 
 * @author wqh
 * 
 * @add by 2016-04-12
 */
public class CarouselImgPrevVo {
	private String id;

	private Integer lineStatus;

	private String picUrl;

	private String lineUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(Integer lineStatus) {
		this.lineStatus = lineStatus;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLineUrl() {
		return lineUrl;
	}

	public void setLineUrl(String lineUrl) {
		this.lineUrl = lineUrl;
	}

}