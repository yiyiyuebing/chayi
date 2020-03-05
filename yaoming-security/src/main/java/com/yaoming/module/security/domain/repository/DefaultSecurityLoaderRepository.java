package com.yaoming.module.security.domain.repository;

import java.util.List;

import com.yaoming.module.security.domain.SecurityFunction;
import com.yaoming.module.security.domain.SecurityResource;

public interface DefaultSecurityLoaderRepository {
	
	public List<SecurityResource> getAllGlobalResources();
	
	public List<SecurityResource> getAllFunctionResources();
	
	public List<SecurityFunction> getAllFunctions();
	
	public List<SecurityFunction> getFunctionsByParent(long parentId);

	public List<SecurityFunction> getRoleFunctions(String roleName);
	
	public SecurityFunction getFunctionById(long id);
}
