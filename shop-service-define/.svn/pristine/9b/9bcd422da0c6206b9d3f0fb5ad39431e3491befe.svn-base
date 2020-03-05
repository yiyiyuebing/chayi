package pub.makers.shop.store.vo;

import pub.makers.shop.store.entity.VtwoStoreRole;

import java.io.Serializable;
import java.util.Date;

public class VtwoStoreRoleVo implements Serializable{
	/** 用户代理商关联表 */
	private String id;
	
	/**  */
	private String storeId;
	
	/** 代理商等级 */
	private String roleId;
	
	/** 联系人 */
	private String concatName;		
	
	/** 联系人电话 */
	private String concatPhone;		
	
	/** 地区id */
	private String regionId;
	
	/** 推荐用户 */
	private String recommendUserId;
	
	/** 身份证正面 */
	private String idCardIndex;		
	
	/** 身份证反面 */
	private String idCardBack;		
	
	/** 营业执照 */
	private String businessLicence;		
	
	/** 税务登记照 */
	private String taxPhoto;		
	
	/** 主营业务描述 */
	private String businessContent;		
	
	/**  */
	private Date createTime;		
	
	/**  */
	private Date updateTime;		
	
	/**  */
	private String createBy;		
	
	/**  */
	private String updateBy;		
	
	/** s审核状态0无 1待 2已-1未 3：待区审核 4：区审核不通过 */
	private Integer status;		
	
	/** 推荐人名称 */
	private String recommendUser;		
	
	/** 审核失败原因 */
	private String reason;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getConcatName() {
		return concatName;
	}

	public void setConcatName(String concatName) {
		this.concatName = concatName;
	}

	public String getConcatPhone() {
		return concatPhone;
	}

	public void setConcatPhone(String concatPhone) {
		this.concatPhone = concatPhone;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRecommendUserId() {
		return recommendUserId;
	}

	public void setRecommendUserId(String recommendUserId) {
		this.recommendUserId = recommendUserId;
	}

	public String getIdCardIndex() {
		return idCardIndex;
	}

	public void setIdCardIndex(String idCardIndex) {
		this.idCardIndex = idCardIndex;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}

	public String getTaxPhoto() {
		return taxPhoto;
	}

	public void setTaxPhoto(String taxPhoto) {
		this.taxPhoto = taxPhoto;
	}

	public String getBusinessContent() {
		return businessContent;
	}

	public void setBusinessContent(String businessContent) {
		this.businessContent = businessContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRecommendUser() {
		return recommendUser;
	}

	public void setRecommendUser(String recommendUser) {
		this.recommendUser = recommendUser;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public static VtwoStoreRoleVo fromVtwoStoreRole(VtwoStoreRole storeRole) {
		VtwoStoreRoleVo vo = new VtwoStoreRoleVo();
		vo.setId(storeRole.getId().toString());
		vo.setStoreId(storeRole.getStoreId().toString());
		vo.setRoleId(storeRole.getRoleId() == null ? null : storeRole.getRoleId().toString());
		vo.setConcatName(storeRole.getConcatName());
		vo.setConcatPhone(storeRole.getConcatPhone());
		vo.setRegionId(storeRole.getRegionId() == null ? null : storeRole.getRegionId().toString());
		vo.setRecommendUserId(storeRole.getRecommendUserId() == null ? null : storeRole.getRecommendUserId().toString());
		vo.setIdCardIndex(storeRole.getIdCardIndex());
		vo.setIdCardBack(storeRole.getIdCardBack());
		vo.setBusinessLicence(storeRole.getBusinessLicence());
		vo.setTaxPhoto(storeRole.getTaxPhoto());
		vo.setBusinessContent(storeRole.getBusinessContent());
		vo.setCreateTime(storeRole.getCreateTime());
		vo.setUpdateTime(storeRole.getUpdateTime());
		vo.setCreateBy(storeRole.getCreateBy());
		vo.setUpdateBy(storeRole.getUpdateBy());
		vo.setStatus(storeRole.getStatus());
		vo.setRecommendUser(storeRole.getRecommendUser());
		vo.setReason(storeRole.getReason());
		return vo;
	}
}
