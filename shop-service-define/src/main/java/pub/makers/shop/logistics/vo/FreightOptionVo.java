package pub.makers.shop.logistics.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class FreightOptionVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal freight;
	private Map<String, FreightVo> options;
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Map<String, FreightVo> getOptions() {
		return options;
	}
	public void setOptions(Map<String, FreightVo> options) {
		this.options = options;
	}
	
}
