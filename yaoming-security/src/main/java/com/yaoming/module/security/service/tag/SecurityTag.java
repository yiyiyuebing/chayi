package com.yaoming.module.security.service.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yaoming.module.security.service.SecurityFunctionService;

@Configurable
public class SecurityTag extends TagSupport{
	private static final long serialVersionUID = 1L;

	private static final String USERNAME = "name";
	
	@Autowired private SecurityFunctionService functionService;
	
	private Logger logger = LoggerFactory.getLogger(SecurityTag.class);
	
	private String name;
	
	@Override
	public int doStartTag() {
		try{
			String username = getUsername();
			if(username==null){
				logger.debug("未找到登录用户");
				return getDefaultBodyAttr();
			}
			if(functionService.isUserHasFunctionAuthority(username, name))
				return EVAL_PAGE;
			return SKIP_BODY;
		}catch(Exception e){
			logger.debug("权限标签出错", e);
		}
		return getDefaultBodyAttr();
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDefaultBodyAttr() {
		switch (functionService.getDefaultBodyAttr()) {
		case SecurityFunctionService.SKIP_BODY: return SKIP_BODY;
		case SecurityFunctionService.EVAL_PAGE: return EVAL_PAGE;
		default: return SKIP_BODY;
		}
	}

	private String getUsername(){
		if ((SecurityContextHolder.getContext() == null)
				|| !(SecurityContextHolder.getContext() instanceof SecurityContext)
				|| (SecurityContextHolder.getContext().getAuthentication() == null))
			return null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() == null)
			return null;
		try {
			BeanWrapperImpl wrapper = new BeanWrapperImpl(auth);
			return String.valueOf(wrapper.getPropertyValue(USERNAME));
		}catch (BeansException e) {
			return null;
		}
	}
	
}
