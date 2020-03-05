package com.yaoming.module.security.dao.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yaoming.module.security.dao.SecurityFunctionExtendMapper;
import com.yaoming.module.security.dao.SecurityFunctionResourcePatternExtendMapper;
import com.yaoming.module.security.dao.SecurityGlobalResourcePatternExtendMapper;
import com.yaoming.module.security.dao.base.po.SecurityFunctionResourcePattern;
import com.yaoming.module.security.dao.base.po.SecurityGlobalResourcePattern;
import com.yaoming.module.security.dao.po.SecurityFunctionExtendPo;
import com.yaoming.module.security.domain.SecurityFunction;
import com.yaoming.module.security.domain.SecurityResource;
import com.yaoming.module.security.domain.impl.DefaultSecurityFunction;
import com.yaoming.module.security.domain.impl.DefaultSecurityResource;
import com.yaoming.module.security.domain.repository.DefaultSecurityLoaderRepository;

@Repository
public class DefaultSecurityLoaderRepositoryImpl implements DefaultSecurityLoaderRepository {

	@Autowired private SecurityGlobalResourcePatternExtendMapper globalResourceDao;
	@Autowired private SecurityFunctionResourcePatternExtendMapper functionResourceDao;
	@Autowired private SecurityFunctionExtendMapper functionDao;

	@Override
	public List<SecurityResource> getAllGlobalResources() {
		List<SecurityResource> result = new ArrayList<>();
		List<SecurityGlobalResourcePattern> patterns = globalResourceDao.selectAll();
		for (SecurityGlobalResourcePattern pattern : patterns)
			result.add(new DefaultSecurityResource(pattern.getId(), pattern.getPattern(), pattern.getNote(), pattern.getAuthority()));
		return result;
	}

	@Override
	public List<SecurityResource> getAllFunctionResources() {
		List<SecurityResource> result = new ArrayList<>();
		List<SecurityFunctionResourcePattern> patterns = functionResourceDao.selectAllFunctionResources();
		for (SecurityFunctionResourcePattern pattern : patterns)
			result.add(new DefaultSecurityResource(pattern.getId(), pattern.getPattern(), pattern.getNote()));
		return result;
	}

	@Override
	public List<SecurityFunction> getAllFunctions() {
		return getDoByPo(functionDao.selectAll());
	}

	@Override
	public List<SecurityFunction> getFunctionsByParent(long parentId) {
		return getDoByPo(functionDao.selectByParentId(parentId));
	}
	
	private SecurityFunction getDoByPo(SecurityFunctionExtendPo sf) {
		if(sf==null)
			return null;
		return new DefaultSecurityFunction(sf.getId(), sf.getParentId(), sf.getName(), sf.getUrl(), sf.getIndex(), sf.getNote(), sf.getRoleIds());
	}
	
	private SecurityFunction getDoByPo(com.yaoming.module.security.dao.base.po.SecurityFunction sf) {
		if(sf==null)
			return null;
		return new DefaultSecurityFunction(sf.getId(), sf.getParentId(), sf.getName(), sf.getUrl(), sf.getIndex(), sf.getNote());
	}
	
	private List<SecurityFunction> getDoByPo(List<SecurityFunctionExtendPo> sfList) {
		List<SecurityFunction> result = new ArrayList<>();
		if(sfList!=null)
			for (SecurityFunctionExtendPo sf : sfList)
				result.add(getDoByPo(sf));
		return result;
	}

	@Override
	public List<SecurityFunction> getRoleFunctions(String roleName) {
		return getDoByPo(functionDao.selectByRoleName(roleName));
	}

	@Override
	public SecurityFunction getFunctionById(long id) {
		return getDoByPo(functionDao.selectByPrimaryKey(id));
	}
}
