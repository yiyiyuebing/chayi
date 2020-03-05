package pub.makers.shop.logistics.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreightResultVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 商品的运费详情 */
	private Map<String, Map<String, FreightVo>> goodFreights;
	
	/** 可选物流服务商列表 */
	private List<FreightServicerVo> servicerList;
	
	public Map<String, Map<String, FreightVo>> getGoodFreights() {
		return goodFreights;
	}

	public void setGoodFreights(Map<String, Map<String, FreightVo>> goodFreights) {
		this.goodFreights = goodFreights;
	}

	public List<FreightServicerVo> getServicerList() {
		return servicerList;
	}

	public void setServicerList(List<FreightServicerVo> servicerList) {
		this.servicerList = servicerList;
	}
	
	public void addGoodFreight(String goodSkuId, Map<String, FreightVo> goodFreight){
		if (goodFreights == null){
			goodFreights = new HashMap<String, Map<String, FreightVo>>();
		}
		goodFreights.put(goodSkuId, goodFreight);
	}
}
