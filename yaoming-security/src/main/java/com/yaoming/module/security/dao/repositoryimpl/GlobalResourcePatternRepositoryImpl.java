package com.yaoming.module.security.dao.repositoryimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yaoming.module.security.dao.SecurityGlobalResourcePatternExtendMapper;
import com.yaoming.module.security.dao.base.po.SecurityGlobalResourcePattern;
import com.yaoming.module.security.domain.impl.DefaultSecurityResource;
import com.yaoming.module.security.domain.repository.GlobalResourcePatternRepository;

@Repository
public class GlobalResourcePatternRepositoryImpl implements GlobalResourcePatternRepository {

	@Autowired private SecurityGlobalResourcePatternExtendMapper globalPatternDao;
	
	@Override
	public void save(long oldId, DefaultSecurityResource globalResource) {
		Assert.state(globalResource.getId()>0, "全局资源ID必须大于0。");
		if(oldId<=0)
			globalPatternDao.replace(getPoByDomain(globalResource));
		else
			globalPatternDao.updateByOldId(globalResource.getId(), oldId, globalResource.getPattern(), globalResource.getAuthority(), globalResource.getNote());
	}


	@Override
	public void delete(long id) {
		globalPatternDao.deleteByPrimaryKey(id);
	}

	
	private SecurityGlobalResourcePattern getPoByDomain(DefaultSecurityResource domain){
		SecurityGlobalResourcePattern po = new SecurityGlobalResourcePattern();
		po.setId(domain.getId());
		po.setPattern(domain.getPattern());
		po.setAuthority(domain.getAuthority());
		po.setNote(domain.getNote());
		return po;
	}
}
