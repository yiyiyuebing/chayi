package com.yaoming.module.security.service;

import java.util.List;

import com.yaoming.module.security.domain.SecurityResource;

public interface SecurityResourceService {
	public List<SecurityResource> getAllFunctionResources();
	public List<SecurityResource> getAllGlobalResources();
}
