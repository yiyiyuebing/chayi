package com.yaoming.module.security.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.yaoming.module.security.domain.SecurityResource;
import com.yaoming.module.security.domain.SecurityRole;
import com.yaoming.module.security.domain.impl.DefaultSecurityRole;
import com.yaoming.module.security.service.SecurityResourceService;
import com.yaoming.module.security.service.SecurityRoleService;

@Configurable
public class SecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

	@Autowired private SecurityRoleService roleService; // 角色服务类

	@Autowired private SecurityResourceService resourceService; // 资源服务类

	private String matcher; // 规则标识

	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> result = new HashSet<ConfigAttribute>();
		result.addAll(roleService.getAllRoles());
		return result;
	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		boolean flag = false;
		Set<ConfigAttribute> result = new HashSet<>();
		// 功能权限
		for (SecurityResource securityResource : resourceService.getAllFunctionResources())
			if (getRequestMatcher(request, securityResource.getPattern()).matches(request)){
				result.addAll(roleService.getByResourceId(securityResource.getId()));
				flag = true;
			}
		if(flag && result.isEmpty()){
			result.add(SecurityRole.ROLE_DENYALL);
			return result;
		}
		// 全局权限
		for (SecurityResource securityResource : resourceService.getAllGlobalResources())
			if (getRequestMatcher(request, securityResource.getPattern()).matches(request)){
//				List<ConfigAttribute> result = new ArrayList<>();
				DefaultSecurityRole role = SecurityRole.getRole(securityResource.getAuthority());
				if(role == null)
					result.add(SecurityRole.ROLE_DENYALL);
				else
					result.add(role);
				return result;
			}
		result.add(SecurityRole.ROLE_DENYALL);
		return result;
	}
	
	private RequestMatcher getRequestMatcher(HttpServletRequest request, String uri){
		RequestMatcher requestMatcher = null;
		if (matcher.toLowerCase().equals("ant"))
			requestMatcher = new AntPathRequestMatcher(uri);
		if (matcher.toLowerCase().equals("regex"))
			requestMatcher = new RegexRequestMatcher(uri, request.getMethod(), true);
		return requestMatcher;
	}
}