package com.yaoming.module.security.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import com.yaoming.module.security.domain.impl.DefaultSecurityRole;

public interface SecurityRole extends GrantedAuthority, ConfigAttribute {
	
	Logger logger = LoggerFactory.getLogger(SecurityRole.class);
	
	public static final String PERMIT_ALL = "permitAll";
	public static final String DENY_ALL = "denyAll";
	
	public long getId();
	public String getName();
	public String getNote();

	@Override
	default String getAuthority(){
		return getName();
	}

	@Override
	default String getAttribute() {
		return getName();
	}

	public static DefaultSecurityRole ROLE_PERMITALL = new DefaultSecurityRole(PERMIT_ALL);
	public static DefaultSecurityRole ROLE_DENYALL = new DefaultSecurityRole(DENY_ALL);
	public static DefaultSecurityRole getRole(String authority){
		switch(authority){
		case PERMIT_ALL: return ROLE_PERMITALL;
		case DENY_ALL: return ROLE_DENYALL;
		default: 
			logger.debug("未定义全局权限 {}", authority);
			return null;
		}
	}
}
