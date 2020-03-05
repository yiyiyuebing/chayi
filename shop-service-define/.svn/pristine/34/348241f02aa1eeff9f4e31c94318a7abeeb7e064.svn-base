package pub.makers.shop.tradeGoods.vo;

import java.io.Serializable;


/*
 * SKU 检查结果
 */
public class GoodCheckResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 是否成功 */
	private boolean success;
	
	/** 错误消息 */
	private String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static GoodCheckResult createError(String msg){
		
		GoodCheckResult result = new GoodCheckResult();
		result.setMessage(msg);
		result.setSuccess(false);
		
		return result;
	}
	
	public static GoodCheckResult createSuccess(){
		
		GoodCheckResult result = new GoodCheckResult();
		result.setSuccess(true);
		
		return result;
	}
}
