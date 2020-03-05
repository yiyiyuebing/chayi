package com.yaoming.module.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaoming.module.security.service.SecurityEditService;
@Controller("securityGlobalEditController")
@RequestMapping("/security/global")
public class SecurityGlobalEditController {
	
	@Autowired private SecurityEditService securityEditService;

	@RequestMapping("/getlist")
	@ResponseBody
	public Map<String, Object> getlist(){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.getGlobalResourcePatterns());
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(
			@RequestParam("id") long id,
			@RequestParam("idOld") long idOld,
			@RequestParam("pattern") String pattern,
			@RequestParam("authority") String authority,
			@RequestParam("note") String note){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.saveGlobalResourcePattern(id, idOld, pattern, authority, note));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(
			@RequestParam("id") long id){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.deleteGlobalResourcePattern(id));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
}
