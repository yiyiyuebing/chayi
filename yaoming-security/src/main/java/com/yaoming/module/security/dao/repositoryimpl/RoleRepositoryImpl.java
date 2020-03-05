package com.yaoming.module.security.dao.repositoryimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaoming.module.security.dao.SecurityFunctionResourcePatternExtendMapper;
import com.yaoming.module.security.dao.SecurityRoleExtendMapper;
import com.yaoming.module.security.dao.SecurityRoleFunctionExtendMapper;
import com.yaoming.module.security.dao.SecurityRoleUserExtendMapper;
import com.yaoming.module.security.dao.base.po.SecurityFunctionResourcePattern;
import com.yaoming.module.security.dao.base.po.SecurityRoleFunction;
import com.yaoming.module.security.domain.SecurityRole;
import com.yaoming.module.security.domain.impl.DefaultSecurityRole;
import com.yaoming.module.security.domain.repository.RoleRepository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired private SecurityFunctionResourcePatternExtendMapper functionResourceDao;
	@Autowired private SecurityRoleFunctionExtendMapper roleFunctionDao;
	@Autowired private SecurityRoleExtendMapper roleDao;
	@Autowired private SecurityRoleUserExtendMapper roleUserDao;

	@Override
	public List<SecurityRole> getRoleList() {
		List<SecurityRole> result = new ArrayList<>();
		List<com.yaoming.module.security.dao.base.po.SecurityRole> patterns = roleDao.selectAll();
		for (com.yaoming.module.security.dao.base.po.SecurityRole pattern : patterns)
			result.add(new DefaultSecurityRole(pattern.getId(), pattern.getName(), pattern.getNote()));
		return result;
	}

	@Override
	public Map<Long, List<SecurityRole>> getAllResourceRoles() {
		Map<Long, List<SecurityRole>> result = new HashMap<>();
		List<SecurityRole> roleList = getRoleList();
		List<SecurityRoleFunction> roleFunctionList = roleFunctionDao.selectAll();
		List<SecurityFunctionResourcePattern> resourceList = functionResourceDao.selectAllFunctionResources();
		for (SecurityFunctionResourcePattern resource : resourceList) {
			List<SecurityRole> list = new ArrayList<>();
			for (SecurityRoleFunction roleFunction : roleFunctionList) {
				if(roleFunction.getFunctionId().longValue()==resource.getFunctionId().longValue())
					for (SecurityRole role : roleList)
						if(role.getId()==roleFunction.getRoleId())
							list.add(role);
			}
			result.put(resource.getId(), list);
		}
		return result;
	}

	@Override
	public List<SecurityRole> getRolesByResourceId(long resourceId) {
		List<SecurityRole> result = new ArrayList<>();
		List<com.yaoming.module.security.dao.base.po.SecurityRole> patterns = roleDao.selectByResourceId(resourceId);
		for (com.yaoming.module.security.dao.base.po.SecurityRole pattern : patterns)
			result.add(new DefaultSecurityRole(pattern.getId(), pattern.getName(), pattern.getNote()));
		return result;
	}

	@Override
	public List<SecurityRole> getRolesByUserId(long userId) {
		List<SecurityRole> result = new ArrayList<>();
		List<com.yaoming.module.security.dao.base.po.SecurityRole> patterns = roleDao.selectByUserId(userId);
		for (com.yaoming.module.security.dao.base.po.SecurityRole pattern : patterns)
			result.add(new DefaultSecurityRole(pattern.getId(), pattern.getName(), pattern.getNote()));
		return result;
	}
	
	@Override
	public void save(SecurityRole securityRole) {
		roleDao.replace(getPoByDomain(securityRole));
	}

	@Override
	public void delete(long id) {
		roleDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<Long> getRoleFunctions(long roleId) {
		List<SecurityRoleFunction> list = roleFunctionDao.selectByRoleId(roleId);
		List<Long> result = new ArrayList<>();
		for (SecurityRoleFunction rf : list)
			if(rf!=null)
				result.add(rf.getFunctionId());
		return result;
	}

	@Override
	public void deleteAllRoleFunctions(long roleId) {
		roleFunctionDao.deleteAllRelation(roleId);
	}

	@Override
	public void saveRoleFunction(long roleId, long functionId) {
		roleFunctionDao.saveRelation(roleId, functionId);
	}

	@Override
	public void deleteRoleFunction(long roleId, long functionId) {
		roleFunctionDao.deleteRelation(roleId, functionId);
	}

	@Override
	public void saveRoleUser(long roleId, long userId) {
		roleUserDao.saveRelation(roleId, userId);
	}

	@Override
	public void deleteRoleUser(long roleId, long userId) {
		roleUserDao.deleteRelation(roleId, userId);
	}

	@Override
	public void deleteAllUserRoles(long userId) {
		roleUserDao.deleteAllRelations(userId);
	}

	@Override
	public void deleteAllRoleUserRelations(long roleId) {
		roleUserDao.deleteAllRoleRelations(roleId);
	}

	private com.yaoming.module.security.dao.base.po.SecurityRole getPoByDomain(SecurityRole domain){
		com.yaoming.module.security.dao.base.po.SecurityRole po = new com.yaoming.module.security.dao.base.po.SecurityRole();
		po.setId(domain.getId());
		po.setName(domain.getName());
		po.setNote(domain.getNote());
		return po;
	}
}
