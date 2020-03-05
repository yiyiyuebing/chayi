package com.yaoming.module.security.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaoming.module.security.service.SecurityEditService;
import com.yaoming.module.security.service.SecurityFunctionService;
@Controller("securityFunctionEditController")
@RequestMapping("/security/function")
public class SecurityFunctionEditController {
	
	private Logger logger = LoggerFactory.getLogger(SecurityFunctionEditController.class);
	
	@Autowired private SecurityEditService securityEditService;
	@Autowired private SecurityFunctionService securityFunctionService;

	@RequestMapping("/getTreeNode")
	@ResponseBody
	public Map<String, Object> getTreeNode(@RequestParam(value="functionId", defaultValue="0") long functionId){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.getTreeNode(functionId));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/getTree")
	@ResponseBody
	public Map<String, Object> getTree(){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.getTree());
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}

	
	@RequestMapping("/getUserTree")
	@ResponseBody
	public Map<String, Object> getUserTree(){
		Map<String, Object> result = new HashMap<>();
		try{
			Collection<? extends GrantedAuthority> roles = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
			Object[] rolesArray = roles.toArray();
			String[] roleNames = new String[roles.size()];
			for (int i=0; i<rolesArray.length; i++) {
				GrantedAuthority role = (GrantedAuthority) rolesArray[i];
				roleNames[i] = role.getAuthority();
			}
			result.put("data", securityFunctionService.getRoleFunctions(roleNames));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			logger.error("", e);
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/node/save")
	@ResponseBody
	public Map<String, Object> saveNode(
			@RequestParam("id") long id,
			@RequestParam("idOld") long idOld,
			@RequestParam("parentId") long parentId,
			@RequestParam("name") String name,
			@RequestParam("note") String note){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.saveFunctionNode(id, idOld, parentId, name, note));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}

	@RequestMapping("/node/delete")
	@ResponseBody
	public Map<String, Object> deleteNode(
			@RequestParam("id") long id){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.deleteFunctionNode(id));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}

	@RequestMapping("/leaf/save")
	@ResponseBody
	public Map<String, Object> saveLeaf(
			@RequestParam("id") long id,
			@RequestParam("idOld") long idOld,
			@RequestParam("functionId") long functionId,
			@RequestParam("pattern") String pattern,
			@RequestParam("note") String note){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.saveFunctionLeaf(id, idOld, functionId, pattern, note));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}

	@RequestMapping("/leaf/delete")
	@ResponseBody
	public Map<String, Object> deleteLeaf(
			@RequestParam("id") long id){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.deleteFunctionLeaf(id));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
}
