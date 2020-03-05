package com.yaoming.module.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoming.module.security.domain.SecurityLoader;
import com.yaoming.module.security.domain.SecurityResource;
import com.yaoming.module.security.service.SecurityResourceService;

@Service
public class DefaultSecurityResourceServiceImpl implements SecurityResourceService {

	@Autowired SecurityLoader securityLoader;
	
	@Override
	public List<SecurityResource> getAllFunctionResources() {
		return securityLoader.getAllFunctionResources();
	}

	@Override
	public List<SecurityResource> getAllGlobalResources() {
		return securityLoader.getAllGlobalResources();
	}


//	<!-- 		<intercept-url pattern="/captcha" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/public/**" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/school/resetPassword" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/*" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/resources/**" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/school/login" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/school/enumTest" access="permitAll" /> -->
//	<!-- 		<intercept-url pattern="/school/**" access="permitAll" /> -->
	
}
