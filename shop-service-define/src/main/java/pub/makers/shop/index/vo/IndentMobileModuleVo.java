package pub.makers.shop.index.vo;

import pub.makers.shop.baseGood.vo.BaseGoodVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class IndentMobileModuleVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private String id;		
	
	/** 楼层商品分类 */
	private String classifyId;		
	
	/** 模块名称 */
	private String moduleName;		
	
	/** 排序 */
	private Integer sort;		
	
	/** 是否有效 */
	private String isValid;		
	
	/** 删除状态 */
	private String delFlag;		
	
	/** 创建时间 */
	private Date dateCreated;		
	
	/** 更新时间 */
	private Date lastUpdated;

	/** 分类列表 */
	private List<IndentMobileModuleClassifyVo> classifyList;

	/** 商品列表 */
	private List<BaseGoodVo> goodList;
	

	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setClassifyId(String classifyId){
		this.classifyId = classifyId;
	}
	
	public String getClassifyId(){
		return classifyId;
	}
	
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	
	public String getModuleName(){
		return moduleName;
	}
	
	public void setSort(Integer sort){
		this.sort = sort;
	}
	
	public Integer getSort(){
		return sort;
	}
	
	public void setIsValid(String isValid){
		this.isValid = isValid;
	}
	
	public String getIsValid(){
		return isValid;
	}
	
	public void setDelFlag(String delFlag){
		this.delFlag = delFlag;
	}
	
	public String getDelFlag(){
		return delFlag;
	}
	
	public void setDateCreated(Date dateCreated){
		this.dateCreated = dateCreated;
	}
	
	public Date getDateCreated(){
		return dateCreated;
	}
	
	public void setLastUpdated(Date lastUpdated){
		this.lastUpdated = lastUpdated;
	}
	
	public Date getLastUpdated(){
		return lastUpdated;
	}

	public List<IndentMobileModuleClassifyVo> getClassifyList() {
		return classifyList;
	}

	public void setClassifyList(List<IndentMobileModuleClassifyVo> classifyList) {
		this.classifyList = classifyList;
	}

	public List<BaseGoodVo> getGoodList() {
		return goodList;
	}

	public void setGoodList(List<BaseGoodVo> goodList) {
		this.goodList = goodList;
	}
}
