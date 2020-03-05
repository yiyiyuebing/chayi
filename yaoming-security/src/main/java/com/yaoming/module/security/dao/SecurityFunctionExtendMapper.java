package com.yaoming.module.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yaoming.module.security.dao.base.SecurityFunctionMapper;
import com.yaoming.module.security.dao.base.po.SecurityFunction;
import com.yaoming.module.security.dao.po.SecurityFunctionExtendPo;

public interface SecurityFunctionExtendMapper extends SecurityFunctionMapper {
	
	public List<SecurityFunctionExtendPo> selectAll();
	
	public List<SecurityFunctionExtendPo> selectByParentId(long parentId);

	public List<SecurityFunctionExtendPo> selectByRoleName(String roleName);

	public void replace(SecurityFunction record);
	
	public void updateByOldId(
			@Param("id") long id,
			@Param("oldId") long oldId,
			@Param("parentId") long parentId,
			@Param("name") String name,
			@Param("note") String note);
	
}