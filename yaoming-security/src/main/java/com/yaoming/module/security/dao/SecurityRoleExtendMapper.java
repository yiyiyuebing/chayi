package com.yaoming.module.security.dao;

import java.util.List;

import com.yaoming.module.security.dao.base.SecurityRoleMapper;
import com.yaoming.module.security.dao.base.po.SecurityRole;

public interface SecurityRoleExtendMapper extends SecurityRoleMapper {
	public List<SecurityRole> selectAll();
	public List<SecurityRole> selectByResourceId(long resourceId);
	public List<SecurityRole> selectByUserId(long userId);
	public int replace(SecurityRole record);
	
}