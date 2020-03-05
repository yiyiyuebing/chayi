package com.yaoming.module.security.domain.repository;

import com.yaoming.module.security.domain.impl.DefaultSecurityResource;

public interface GlobalResourcePatternRepository {
	public void save(long oldId, DefaultSecurityResource globalResource);
	public void delete(long id);
}
