package com.yaoming.module.security.dao;

import org.apache.ibatis.annotations.Param;

import com.yaoming.module.security.dao.base.SecurityRoleUserMapper;
import com.yaoming.module.security.dao.base.po.SecurityRoleUser;

public interface SecurityRoleUserExtendMapper extends SecurityRoleUserMapper {
	
	public SecurityRoleUser selectByUserid(long userid);
	public void saveRelation(@Param("roleId") long roleId, @Param("userId") long userId);
	public void deleteRelation(@Param("roleId") long roleId, @Param("userId") long userId);
	public void deleteAllRelations(long userId);
	public void deleteAllRoleRelations(long roleId);
}