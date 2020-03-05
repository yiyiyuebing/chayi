package com.yaoming.module.security.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoming.module.security.domain.SecurityLoader;
import com.yaoming.module.security.domain.SecurityRole;
import com.yaoming.module.security.service.SecurityRoleService;

@Service
public class DefaultSecurityRoleServiceImpl implements SecurityRoleService {

	@Autowired private SecurityLoader securityLoader;
	
	@Override
	public List<SecurityRole> getByResourceId(long resourceId) {
		return securityLoader.getRolesByResourceId(resourceId);
	}

	@Override
	public Set<SecurityRole> getAllRoles() {
		return securityLoader.getAllRoles();
	}

}
