package com.yaoming.module.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoming.common.util.IdGenerator;
import com.yaoming.module.security.domain.SecurityFunction;
import com.yaoming.module.security.domain.SecurityLoader;
import com.yaoming.module.security.domain.SecurityResource;
import com.yaoming.module.security.domain.SecurityRole;
import com.yaoming.module.security.domain.TreeNode.FunctionLeaf;
import com.yaoming.module.security.domain.TreeNode.FunctionNode;
import com.yaoming.module.security.domain.impl.DefaultSecurityResource;
import com.yaoming.module.security.domain.impl.DefaultSecurityRole;
import com.yaoming.module.security.domain.repository.GlobalResourcePatternRepository;
import com.yaoming.module.security.domain.repository.RoleRepository;
import com.yaoming.module.security.domain.repository.TreeNodeRepository;
import com.yaoming.module.security.service.SecurityEditService;
import com.yaoming.module.security.service.vo.SecurityGlobalResourcePatternVO;
import com.yaoming.module.security.service.vo.SecurityRoleVO;
import com.yaoming.module.security.service.vo.SecurityTreeNodeVO;
import com.yaoming.module.security.service.vo.SecurityTreeNodeVO.SecurityFunctionTreeNodeVO;
import com.yaoming.module.security.service.vo.SecurityTreeNodeVO.SecurityResourcePatternLeafVO;

@Service
public class SecurityEditServiceImpl implements SecurityEditService {

	@Autowired private GlobalResourcePatternRepository globalResourcePatternRepository;
	@Autowired private TreeNodeRepository treeNodeRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private SecurityLoader securityLoader;
	
	@Override
	public List<SecurityGlobalResourcePatternVO> getGlobalResourcePatterns() {
		List<SecurityResource> list = securityLoader.getAllGlobalResources();
		List<SecurityGlobalResourcePatternVO> result = new ArrayList<>();
		for (SecurityResource securityResource : list)
			result.add(new SecurityGlobalResourcePatternVO(securityResource));
		return result;
	}

	@Override
	public long saveGlobalResourcePattern(long id, long idOld, String pattern, String authority, String note) {
		globalResourcePatternRepository.save(idOld, new DefaultSecurityResource(id, pattern, note, authority));
		securityLoader.reload();
		return id;
	}

	@Override
	public boolean deleteGlobalResourcePattern(long id) {
		globalResourcePatternRepository.delete(id);
		securityLoader.reload();
		return true;
	}

	@Override
	public Map<String, List<? extends SecurityTreeNodeVO>> getTreeNode(long functionId) {
		Map<String, List<? extends SecurityTreeNodeVO>> result = new HashMap<>();
		
		List<FunctionNode> functionNodeList = getFunctionNodeList(functionId);
		List<SecurityFunctionTreeNodeVO> nodeList = new ArrayList<>();
		for (FunctionNode fn : functionNodeList)
			nodeList.add(new SecurityFunctionTreeNodeVO(fn));
		result.put("nodeList", nodeList);
		
		List<FunctionLeaf> functionLeafList = treeNodeRepository.getFunctionLeafList(functionId);
		List<SecurityResourcePatternLeafVO> leafList = new ArrayList<>();
		for (FunctionLeaf fl : functionLeafList)
			leafList.add(new SecurityResourcePatternLeafVO(fl));
		result.put("leafList", leafList);
		
		return result;
	}

	@Override
	public List<SecurityFunctionTreeNodeVO> getTree() {
		return getTree(null);
	}
	private List<SecurityFunctionTreeNodeVO> getTree(SecurityFunctionTreeNodeVO parent) {
		List<SecurityFunctionTreeNodeVO> result = new ArrayList<>();
		List<FunctionNode> functionNodeList = getFunctionNodeList(parent==null ? 0 : parent.getId());
		for (FunctionNode fn : functionNodeList) {
			SecurityFunctionTreeNodeVO functionVo = new SecurityFunctionTreeNodeVO(fn);
			result.add(functionVo);
			functionVo.addAllChildren(getTree(functionVo));
		}
		return result;
	}
	
	private List<FunctionNode> getFunctionNodeList(long parentId) {
		List<FunctionNode> result = new ArrayList<>();
		List<SecurityFunction> sfList = securityLoader.getFunctionsByParentId(parentId);
		if(sfList!=null)
			for (SecurityFunction sf : sfList)
				result.add(new FunctionNode(sf.getId(), parentId, sf.getName(), sf.getNote()));
		return result;
	}

	@Override
	public long saveFunctionNode(long id, long idOld, long parentId, String name, String note) {
		treeNodeRepository.saveFunctionNode(idOld, new FunctionNode(id, parentId, name, note));
		securityLoader.reload();
		return id;
	}

	@Override
	public boolean deleteFunctionNode(long id) {
		treeNodeRepository.deleteFunctionNode(id);
		securityLoader.reload();
		return true;
	}

	@Override
	public long saveFunctionLeaf(long id, long idOld, long functionId, String pattern, String note) {
		treeNodeRepository.saveFunctionLeaf(idOld, new FunctionLeaf(id, functionId, pattern, note));
		securityLoader.reload();
		return id;
	}

	@Override
	public boolean deleteFunctionLeaf(long id) {
		treeNodeRepository.deleteFunctionLeaf(id);
		securityLoader.reload();
		return true;
	}

	@Override
	public List<SecurityRoleVO> getRoleList() {
		List<SecurityRole> list = roleRepository.getRoleList();
		List<SecurityRoleVO> result = new ArrayList<>();
		for (SecurityRole sr : list)
			result.add(new SecurityRoleVO(sr));
		return result;
	}

	@Override
	public long saveRole(long id, String name, String note) {
		if(id<=0)
			id = IdGenerator.getDefault().nextId();
		roleRepository.save(new DefaultSecurityRole(id, name, note));
		securityLoader.reload();
		return id;
	}

	@Override
	public boolean deleteRole(long id) {
		roleRepository.deleteAllRoleFunctions(id);
		roleRepository.deleteAllRoleUserRelations(id);
		roleRepository.delete(id);
		securityLoader.reload();
		return true;
	}

	@Override
	public List<Long> getSelectedFunctions(long roleId) {
		return roleRepository.getRoleFunctions(roleId);
	}

	@Override
	public boolean roleFuncSave(long roleId, long[] functionIds) {
		roleRepository.deleteAllRoleFunctions(roleId);
		return addRoleFunctions(roleId, functionIds);
	}

	@Override
	public boolean addRoleFunctions(long roleId, long[] functionIds) {
		if(functionIds!=null)
			for (long functionId : functionIds)
				roleRepository.saveRoleFunction(roleId, functionId);
		securityLoader.reload();
		return true;
	}

	@Override
	public boolean deleteRoleFunctions(long roleId, long[] functionIds) {
		if(functionIds!=null)
			for (long functionId : functionIds)
				roleRepository.deleteRoleFunction(roleId, functionId);
		securityLoader.reload();
		return true;
	}

	@Override
	public List<SecurityRoleVO> getUserRoleList(long userId) {
		List<SecurityRole> list = roleRepository.getRolesByUserId(userId);
		List<SecurityRoleVO> result = new ArrayList<>();
		for (SecurityRole sr : list)
			result.add(new SecurityRoleVO(sr));
		return result;
	}

	@Override
	public boolean addUserRoles(long userId, long[] roleIds) {
		if(roleIds!=null)
			for (long roleId : roleIds)
				roleRepository.saveRoleUser(roleId, userId);
		securityLoader.reload();
		return true;
	}

	@Override
	public boolean deleteUserRoles(long userId, long[] roleIds) {
		if(roleIds!=null)
			for (long roleId : roleIds)
				roleRepository.deleteRoleUser(roleId, userId);
		securityLoader.reload();
		return true;
	}

	@Override
	public boolean saveUserRoles(long userId, long[] roleIds) {
		deleteAllUserRoles(userId);
		return addUserRoles(userId, roleIds);
	}

	@Override
	public void deleteAllUserRoles(long userId) {
		roleRepository.deleteAllUserRoles(userId);
		securityLoader.reload();
	}
}
