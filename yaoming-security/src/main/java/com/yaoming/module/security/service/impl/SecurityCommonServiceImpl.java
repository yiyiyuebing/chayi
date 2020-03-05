package com.yaoming.module.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoming.module.security.domain.SecurityLoader;
import com.yaoming.module.security.service.SecurityCommonService;

@Service
public class SecurityCommonServiceImpl implements SecurityCommonService {

	@Autowired private SecurityLoader securityLoader;

	@Override
	public void reload() {
		securityLoader.reload();
	}

}
