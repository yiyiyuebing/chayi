package pub.makers.shop.logistics.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CarriageRuleDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;		
	
	/** 配送规则id */
	private Long carriageRuleId;		
	
	/** 订单满额 */
	private BigDecimal indentMoneyFull;		
	
	/** 满额邮费 */
	private BigDecimal carriageFull;		
	
	/** 未满额邮费 */
	private BigDecimal carriageNotFull;		
	
	/** 配送区域 */
	private String deliverRegion;		
	

	public void setId(Long id){
		this.id = id;
	}
	
	public Long getId(){
		return id;
	}
	
	public void setCarriageRuleId(Long carriageRuleId){
		this.carriageRuleId = carriageRuleId;
	}
	
	public Long getCarriageRuleId(){
		return carriageRuleId;
	}
	
	public void setIndentMoneyFull(BigDecimal indentMoneyFull){
		this.indentMoneyFull = indentMoneyFull;
	}
	
	public BigDecimal getIndentMoneyFull(){
		return indentMoneyFull;
	}
	
	public void setCarriageFull(BigDecimal carriageFull){
		this.carriageFull = carriageFull;
	}
	
	public BigDecimal getCarriageFull(){
		return carriageFull;
	}
	
	public void setCarriageNotFull(BigDecimal carriageNotFull){
		this.carriageNotFull = carriageNotFull;
	}
	
	public BigDecimal getCarriageNotFull(){
		return carriageNotFull;
	}
	
	public void setDeliverRegion(String deliverRegion){
		this.deliverRegion = deliverRegion;
	}
	
	public String getDeliverRegion(){
		return deliverRegion;
	}
	
}
