package com.yaoming.module.security.domain.repository;

import java.util.List;
import java.util.Map;

import com.yaoming.module.security.domain.SecurityRole;

public interface RoleRepository {
	public List<SecurityRole> getRoleList();
	public Map<Long, List<SecurityRole>> getAllResourceRoles();
	public List<SecurityRole> getRolesByResourceId(long resourceId);
	public List<SecurityRole> getRolesByUserId(long userId);
	public void save(SecurityRole securityRole);
	public void delete(long id);
	public List<Long> getRoleFunctions(long roleId);
	public void saveRoleFunction(long roleId, long functionId);
	public void deleteAllRoleFunctions(long roleId);
	public void deleteRoleFunction(long roleId, long functionId);
	public void saveRoleUser(long roleId, long userId);
	public void deleteRoleUser(long roleId, long userId);
	public void deleteAllUserRoles(long userId);
	public void deleteAllRoleUserRelations(long roleId);
}