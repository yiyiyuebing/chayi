package pub.makers.shop.logistics.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pub.makers.shop.tradeOrder.vo.IndentListVo;

public class FreightVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<IndentListVo> indentList;
	
	/** 运送方式名称 */
	private String methodName;		
	
	/**  */
	private String methodId;	
	
	/** 服务商名称 */
	private String servicerName;
	
	/** 服务商ID */
	private String servicerId;
	
	/** 运费金额 */
	private BigDecimal firstFreight;
	
	private BigDecimal incrFreight;
	
	private BigDecimal totalFreight;
	
	private boolean freeFalg;
	
	/** 模板描述 */
	private String tplDesc;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public String getServicerName() {
		return servicerName;
	}

	public void setServicerName(String servicerName) {
		this.servicerName = servicerName;
	}

	public String getServicerId() {
		return servicerId;
	}

	public void setServicerId(String servicerId) {
		this.servicerId = servicerId;
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

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public List<IndentListVo> getIndentList() {
		return indentList;
	}

	public void setIndentList(List<IndentListVo> indentList) {
		this.indentList = indentList;
	}
	
	public void addIndentListVo(IndentListVo ivo){
		if (indentList == null){
			indentList = new ArrayList<IndentListVo>();
		}
		
		indentList.add(ivo);
	}

	public boolean isFreeFalg() {
		return freeFalg;
	}

	public void setFreeFalg(boolean freeFalg) {
		this.freeFalg = freeFalg;
	}

	public String getTplDesc() {
		return tplDesc;
	}

	public void setTplDesc(String tplDesc) {
		this.tplDesc = tplDesc;
	}
	
	
}
