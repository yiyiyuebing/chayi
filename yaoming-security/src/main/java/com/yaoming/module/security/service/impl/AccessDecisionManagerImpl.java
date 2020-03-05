package com.yaoming.module.security.service.impl;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.yaoming.module.security.domain.SecurityRole;

public class AccessDecisionManagerImpl implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> attributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (attributes!=null)
			for (ConfigAttribute attribute : attributes) {
				String authority;
				if(attribute==null || (authority=attribute.getAttribute())==null)
					continue;
				if(checkGlobalAuthorities(authority))
					return;
				// authority为用户所被赋予的权限, needRole 为访问相应的资源应该具有的权限。
				for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) 
					if (authority.equals(grantedAuthority.getAuthority()))
						return;
			}
		throw new AccessDeniedException("权限不足!");
	}
	
	private boolean checkGlobalAuthorities(String security){
		if(security==null)
			return false;
		switch(security){
		case SecurityRole.PERMIT_ALL:
			return true;
		case SecurityRole.DENY_ALL:
		default: 
			return false;
		}
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}