package com.yaoming.module.security.service;

import java.util.List;
import java.util.Set;

import com.yaoming.module.security.domain.SecurityRole;

public interface SecurityRoleService {
	public List<SecurityRole> getByResourceId(long resourceId);
//	public List<SecurityRole> getByUserId(long userId);
	public Set<SecurityRole> getAllRoles();
}
