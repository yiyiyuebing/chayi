package com.yaoming.module.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yaoming.module.security.dao.base.SecurityRoleFunctionMapper;
import com.yaoming.module.security.dao.base.po.SecurityRoleFunction;

public interface SecurityRoleFunctionExtendMapper extends SecurityRoleFunctionMapper {
	public List<SecurityRoleFunction> selectAll();
	public List<SecurityRoleFunction> selectByRoleId(long roleId);
	public void saveRelation(@Param("roleId") long roleId, @Param("functionId") long functionId);
	public void deleteRelation(@Param("roleId") long roleId, @Param("functionId") long functionId);
	public void deleteAllRelation(long roleId);
}