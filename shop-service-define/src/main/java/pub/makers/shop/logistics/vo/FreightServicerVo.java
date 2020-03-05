package pub.makers.shop.logistics.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 物流服务商信息
 * @author jlr_6
 *
 */
public class FreightServicerVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FreightServicerVo(){}
	
	public FreightServicerVo(String servicerId, String servicerName){
		
		this.servicerId = servicerId;
		this.servicerName = servicerName;
		
	} 
	
	private String servicerId;
	
	private String servicerName;
	
	/** 总运费 */
	private BigDecimal totalFreight;
	private BigDecimal firstFreight;
	private BigDecimal incrFreight;

	public String getServicerId() {
		return servicerId;
	}

	public void setServicerId(String servicerId) {
		this.servicerId = servicerId;
	}

	public String getServicerName() {
		return servicerName;
	}

	public void setServicerName(String servicerName) {
		this.servicerName = servicerName;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public BigDecimal getFirstFreight() {
		return firstFreight;
	}

	public void setFirstFreight(BigDecimal firstFreight) {
		this.firstFreight = firstFreight;
	}

	public BigDecimal getIncrFreight() {
		return incrFreight;
	}

	public void setIncrFreight(BigDecimal incrFreight) {
		this.incrFreight = incrFreight;
	}
	
	
}
