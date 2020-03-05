package com.yaoming.module.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("securityEditController")
@RequestMapping("/security")
public class SecurityEditController {
	
	@RequestMapping("/editor")
	public String main(Model uiModel,HttpServletRequest request) {
		return "security/editor";
	}
}
