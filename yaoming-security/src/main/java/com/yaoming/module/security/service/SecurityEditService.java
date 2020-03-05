package com.yaoming.module.security.service;

import java.util.List;
import java.util.Map;

import com.yaoming.module.security.service.vo.SecurityGlobalResourcePatternVO;
import com.yaoming.module.security.service.vo.SecurityRoleVO;
import com.yaoming.module.security.service.vo.SecurityTreeNodeVO;
import com.yaoming.module.security.service.vo.SecurityTreeNodeVO.SecurityFunctionTreeNodeVO;

public interface SecurityEditService {
	
	// global resource patterns edit begin
	public List<SecurityGlobalResourcePatternVO> getGlobalResourcePatterns();
	public long saveGlobalResourcePattern(long id, long idOld, String pattern, String authority, String note);
	public boolean deleteGlobalResourcePattern(long id);
	// gGlobal resource patterns edit end

	// function tree edit begin
	// include function tree and function resource patterns
	public Map<String, List<? extends SecurityTreeNodeVO>> getTreeNode(long functionId);
	public List<SecurityFunctionTreeNodeVO> getTree();
	public long saveFunctionNode(long id, long idOld, long parentId, String name, String note);
	public boolean deleteFunctionNode(long id);
	public long saveFunctionLeaf(long id, long idOld, long functionId, String pattern, String note);
	public boolean deleteFunctionLeaf(long id);
	// function tree edit end

	// role edit begin
	public List<SecurityRoleVO> getRoleList();
	public long saveRole(long id, String name, String note);
	public boolean deleteRole(long id);
	public List<Long> getSelectedFunctions(long roleId);
	public boolean roleFuncSave(long roleId, long[] functionIds);
	public boolean addRoleFunctions(long roleId, long[] functionIds);
	public boolean deleteRoleFunctions(long roleId, long[] functionIds);
	public List<SecurityRoleVO> getUserRoleList(long userId);
	public boolean saveUserRoles(long userId, long[] roleIds);
	public boolean addUserRoles(long userId, long[] roleIds);
	public boolean deleteUserRoles(long userId, long[] roleIds);
	public void deleteAllUserRoles(long userId);
}
