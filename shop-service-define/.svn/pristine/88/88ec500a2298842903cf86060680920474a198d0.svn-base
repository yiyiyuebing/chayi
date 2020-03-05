package pub.makers.shop.base.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ResultData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5072824736985238210L;
	private int counts;
	private String msg;
	private String errorCode;
	private boolean success;
	private Object data;
	private Long total;
	private int pageNo;
	private int pageSize;

	public ResultData() {

	}

	public ResultData(int counts, Map<String, Object> data) {
		this.counts = counts;
		this.data = data;
	}

	public ResultData(String errorCode, String msg, Object data) {
		this.errorCode = errorCode;
		this.msg = msg;
		this.data = data;
	}

	public ResultData(int counts, String msg, boolean success, String name, Object data) {
		this.counts = counts;
		this.msg = msg;
		this.success = success;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(name, data);
		this.data = dataMap;
	}
	public ResultData(int counts, int pageNo, int pageSize, String msg, boolean success, String name, Object data) {
		this.counts = counts;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.msg = msg;
		this.success = success;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(name, data);
		this.data = dataMap;
	}
	

	public static ResultData createSuccess(){
		
		return new ResultData(0, null, true, "data", null);
	}
	
	public static ResultData createSuccess(Object data){
		
		ResultData result = new ResultData(0, null, true, "data", null);
		result.data = data;
		
		return result;
	}

	public static ResultData createSuccess(int counts, Object data){

		ResultData result = new ResultData(counts, null, true, "data", null);
		result.data = data;

		return result;
	}


	public static ResultData createSuccess(int counts, int pageNo, int pageSize, Object data){

		ResultData result = new ResultData(counts, pageNo, pageSize, null, true, "data", null);
		result.data = data;

		return result;
	}
	
	public static ResultData createSuccess(String msg){
		
		return new ResultData(0, msg, true, "data", null);
	}
	
	public static ResultData createSuccess(String name, Object data) {
		return new ResultData(0, null, true, name, data);
	}
	
	@SuppressWarnings("unchecked")
	public ResultData put(String name, Object data){
		
		if (this.data == null){
			this.data = new HashMap<String, Object>();
		}
		((Map<String, Object>) this.data).put(name, data);
		
		return this;
	}
	
	public static ResultData createFail(String msg) {
		return new ResultData(0, msg, false, "data", null);
	}

	public static ResultData createFail(String errorCode, String msg, Object data) {
		return new ResultData(errorCode, msg, data);
	}

	public static ResultData createFail() {
		return new ResultData(0, null, false, "data", null);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCounts() {
		return counts;
	}

	public Object getData() {
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}
