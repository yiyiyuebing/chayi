package com.yaoming.module.security.domain;

import java.util.List;
import java.util.Set;

public interface SecurityLoader {
	
	public static final boolean DEFAULT_CACHE_STATUS = true;
	
	public void reload();
//	public void setCacheEnabled(boolean cacheEnabled);
	
	public List<SecurityResource> getAllFunctionResources();
	public List<SecurityResource> getAllGlobalResources();
	public List<SecurityFunction> getAllFunctions();
	
	public List<SecurityFunction> getFunctionsByParentId(long parentId);
	
	public Set<SecurityRole> getAllRoles();
	public List<SecurityRole> getRolesByResourceId(long resourceId);
	
	public void globalResourceChange();
	public void functionResourceChange();
	public void roleChange();

	public List<SecurityFunctionTree> getRoleFunctions(String... roleNames);
	
}
