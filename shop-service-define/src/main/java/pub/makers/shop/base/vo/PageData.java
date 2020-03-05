package pub.makers.shop.base.vo;

import java.io.Serializable;

public class PageData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3987796867463079838L;
	
	
	private Long total;
	private Object data;
	
	public PageData(Long total, Object data){
		this.total = total;
		this.data = data;
	}
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	

}
