package com.yaoming.module.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaoming.common.util.StringUtil;
import com.yaoming.module.security.service.SecurityEditService;
@Controller("securityRoleEditController")
@RequestMapping("/security/role")
public class SecurityRoleEditController {
	
	@Autowired private SecurityEditService securityEditService;

	@RequestMapping("/getlist")
	@ResponseBody
	public Map<String, Object> getlist(){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.getRoleList());
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/function/getlist")
	@ResponseBody
	public Map<String, Object> getRoleFunctions(@RequestParam("roleId") long roleId){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.getSelectedFunctions(roleId));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/function/save")
	@ResponseBody
	public Map<String, Object> roleFuncSave(
			@RequestParam("roleId") long roleId, 
			@RequestParam("functionIds") String functionIds){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.roleFuncSave(roleId, StringUtil.toLongArray(functionIds, ",")));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/function/add")
	@ResponseBody
	public Map<String, Object> addRoleFunctions(
			@RequestParam("roleId") long roleId, 
			@RequestParam("functionIds") String functionIds){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.addRoleFunctions(roleId, StringUtil.toLongArray(functionIds, ",")));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/function/delete")
	@ResponseBody
	public Map<String, Object> deleteRoleFunctions(
			@RequestParam("roleId") long roleId, 
			@RequestParam("functionIds") String functionIds){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.deleteRoleFunctions(roleId, StringUtil.toLongArray(functionIds, ",")));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/user/getlist")
	@ResponseBody
	public Map<String, Object> getUserRoles(@RequestParam("userId") long userId){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.getUserRoleList(userId));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/user/add")
	@ResponseBody
	public Map<String, Object> addUserRoles(
			@RequestParam("userId") long userId, 
			@RequestParam("roleIds") String roleIds){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.addUserRoles(userId, StringUtil.toLongArray(roleIds, ",")));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
	
	@RequestMapping("/user/delete")
	@ResponseBody
	public Map<String, Object> deleteUserRoles(
			@RequestParam("userId") long userId, 
			@RequestParam("roleIds") String roleIds){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.deleteUserRoles(userId, StringUtil.toLongArray(roleIds, ",")));
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
			@RequestParam("name") String name,
			@RequestParam("note") String note){
		Map<String, Object> result = new HashMap<>();
		try{
			result.put("data", securityEditService.saveRole(id, name, note));
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
			result.put("data", securityEditService.deleteRole(id));
			result.put("code", "SUCCESS");
			result.put("msg", "成功");
		}catch(Exception e){
			result.put("code", "FAIL");
			result.put("msg", "失败");
		}
		return result;
	}
}
