package pub.makers.shop.baseOrder.pojo;

import java.io.Serializable;

/**
 * 查询交易上下文
 * @author apple
 *
 */
public class TradeContext implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String clientIp;
	private String regionId;
	private String area;
	private String city;
	private String buyerId;
	
	
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
}