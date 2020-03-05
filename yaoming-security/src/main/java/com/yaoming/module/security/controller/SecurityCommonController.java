package com.yaoming.module.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaoming.module.security.service.SecurityCommonService;
@Controller("securityCommonController")
@RequestMapping("/security/common")
public class SecurityCommonController {
	
	@Autowired private SecurityCommonService securityCommonService;

	@RequestMapping("/reload")
	@ResponseBody
	public Map<String, Object> reload(){
		Map<String, Object> result = new HashMap<>();
		try{
			securityCommonService.reload();
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
}
