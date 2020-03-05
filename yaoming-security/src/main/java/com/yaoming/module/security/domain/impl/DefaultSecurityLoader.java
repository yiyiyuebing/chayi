package com.yaoming.module.security.domain.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yaoming.module.security.domain.SecurityFunction;
import com.yaoming.module.security.domain.SecurityFunctionTree;
import com.yaoming.module.security.domain.SecurityLoader;
import com.yaoming.module.security.domain.SecurityResource;
import com.yaoming.module.security.domain.SecurityRole;
import com.yaoming.module.security.domain.repository.DefaultSecurityLoaderRepository;
import com.yaoming.module.security.domain.repository.RoleRepository;

@Component("securityLoader")
public class DefaultSecurityLoader implements SecurityLoader {

	@Autowired private DefaultSecurityLoaderRepository defaultSecurityLoaderRepository;
	@Autowired private RoleRepository roleRepository;
	
	private boolean cacheEnabled = DEFAULT_CACHE_STATUS;

	private List<SecurityResource> globalResourceList;
	private List<SecurityResource> functionResourceList;
	
	private List<SecurityFunction> functionList;
	private Map<String, List<SecurityFunction>> roleFunctionMap = new HashMap<>();;
	
	private Map<Long, List<SecurityRole>> resourceRoleMap;

	private ReadWriteLock functionResourceListLock = new ReentrantReadWriteLock();
	private ReadWriteLock globalResourceListLock = new ReentrantReadWriteLock();
	private ReadWriteLock resourceRoleMapLock = new ReentrantReadWriteLock();
	private ReadWriteLock roleFunctionMapLock = new ReentrantReadWriteLock();
	private ReadWriteLock functionListLock = new ReentrantReadWriteLock();
	
//	@Override
//	public void setCacheEnabled(boolean cacheEnabled) {
//		this.cacheEnabled = cacheEnabled;
//	}

	@Override
	public List<SecurityResource> getAllGlobalResources() {
		if(!cacheEnabled)
			return defaultSecurityLoaderRepository.getAllGlobalResources();
		if(globalResourceList==null){
			try{
				globalResourceListLock.writeLock().lock();
				if(globalResourceList==null)
					globalResourceList = defaultSecurityLoaderRepository.getAllGlobalResources();
			}finally{
				globalResourceListLock.writeLock().unlock();
			}
		}
		return globalResourceList;
	}

	@Override
	public List<SecurityResource> getAllFunctionResources() {
		if(!cacheEnabled)
			return defaultSecurityLoaderRepository.getAllFunctionResources();
		if(functionResourceList==null){
			try{
				functionResourceListLock.writeLock().lock();
				if(functionResourceList==null)
					functionResourceList = defaultSecurityLoaderRepository.getAllFunctionResources();
			}finally{
				functionResourceListLock.writeLock().unlock();
			}
		}
		return functionResourceList;
	}

	@Override
	public List<SecurityFunction> getAllFunctions() {
		if(!cacheEnabled)
			return defaultSecurityLoaderRepository.getAllFunctions();
		if(functionList==null){
			try{
				functionListLock.writeLock().lock();
				if(functionList==null)
					functionList = defaultSecurityLoaderRepository.getAllFunctions();
			}finally{
				functionListLock.writeLock().unlock();
			}
		}
		return functionList;
	}

	@Override
	public List<SecurityRole> getRolesByResourceId(long resourceId) {
		if(!cacheEnabled)
			return roleRepository.getRolesByResourceId(resourceId);
		checkAndLoadResourceRoleMap();
		if(resourceRoleMap.containsKey(resourceId))
			return resourceRoleMap.get(resourceId);
		return new ArrayList<>();
	}

	@Override
	public Set<SecurityRole> getAllRoles() {
		if(!cacheEnabled){
			Set<SecurityRole> result = new HashSet<>();
			result.addAll(roleRepository.getRoleList());
			return result;
		}
		checkAndLoadResourceRoleMap();
		Set<SecurityRole> result = new HashSet<>();
		for(List<SecurityRole> list: resourceRoleMap.values())
			result.addAll(list);
		return result;
	}
	
	private void checkAndLoadResourceRoleMap(){
		if(resourceRoleMap==null){
			try{
				resourceRoleMapLock.writeLock().lock();
				if(resourceRoleMap==null)
					resourceRoleMap = roleRepository.getAllResourceRoles();
			}finally{
				resourceRoleMapLock.writeLock().unlock();
			}
		}
	}

	@Override
	public void globalResourceChange() {
		try{
			globalResourceListLock.writeLock().lock();
			globalResourceList = null;
		} finally {
			globalResourceListLock.writeLock().unlock();
		}
	}

	@Override
	public void functionResourceChange() {
		try{
			functionListLock.writeLock().lock();
			functionResourceListLock.writeLock().lock();
			resourceRoleMapLock.writeLock().lock();
			
			functionList = null;
			functionResourceList = null;
			resourceRoleMap = null;
		} finally {
			functionListLock.writeLock().unlock();
			functionResourceListLock.writeLock().unlock();
			resourceRoleMapLock.writeLock().unlock();
		}
	}

	@Override
	public void roleChange() {
		try{
			resourceRoleMapLock.writeLock().lock();
			resourceRoleMap = null;
		} finally {
			resourceRoleMapLock.writeLock().unlock();
		}
	}

	@Override
	public List<SecurityFunction> getFunctionsByParentId(long parentId) {
		if(!cacheEnabled)
			return defaultSecurityLoaderRepository.getFunctionsByParent(parentId);
		List<SecurityFunction> result = new ArrayList<>();
		try{
			functionListLock.readLock().lock();
			for (SecurityFunction sf : getAllFunctions())
				if(sf.getParentId()==parentId)
					result.add(sf);
		}finally {
			functionListLock.readLock().unlock();
		}
		return result;
	}

	@Override
	public List<SecurityFunctionTree> getRoleFunctions(String... roleNames) {
		List<SecurityFunctionTree> result = new ArrayList<>();
		for (String roleName : roleNames)
			for (SecurityFunction sf : getRoleFunctionList(roleName))
				treeAddFunctionList(result, getFunctionListWithParents(sf));
		return result;
	}
	
	private void treeAddFunctionList(List<SecurityFunctionTree> tree, List<SecurityFunction> list) {
		for (SecurityFunction function : list) {
			boolean flag = false;
			for (SecurityFunctionTree functionTreeNode : tree)
				if(function.getId()==functionTreeNode.getId()) {
					flag = true;
					tree = functionTreeNode.getChildren();
					break;
				}
			if(!flag) {
				SecurityFunctionTree functionTreeNode = new DefaultSecurityFunctionTree(function);
				tree.add(functionTreeNode);
				Collections.sort(tree);
				tree = functionTreeNode.getChildren();
			}
		}
	}
	
	private List<SecurityFunction> getRoleFunctionList(String roleName) {
		if(!cacheEnabled)
			return defaultSecurityLoaderRepository.getRoleFunctions(roleName);
		try{
			roleFunctionMapLock.readLock().lock();
			if(roleFunctionMap.containsKey(roleName))
				return roleFunctionMap.get(roleName);
		}finally {
			roleFunctionMapLock.readLock().unlock();
		}
		List<SecurityFunction> list = defaultSecurityLoaderRepository.getRoleFunctions(roleName);
		if(list==null)
			return new ArrayList<>();
		try {
			roleFunctionMapLock.writeLock().lock();
			roleFunctionMap.put(roleName, list);
			return list;
		}finally {
			roleFunctionMapLock.writeLock().unlock();
		}
	}
	
	private List<SecurityFunction> getFunctionListWithParents(SecurityFunction sf) {
		List<SecurityFunction> list = new ArrayList<>();
		if(sf==null)
			return list;
		list.add(sf);
		while(sf!=null && sf.getParentId()!=SecurityFunction.ROOT_PARENT_ID) {
			sf = getFunctionById(sf.getParentId());
			if(sf!=null)
				list.add(0, sf);
		}
		return list;
	}
	
	private SecurityFunction getFunctionById(long id) {
		if(!cacheEnabled)
			return defaultSecurityLoaderRepository.getFunctionById(id);
		for (SecurityFunction function : getAllFunctions())
			if(function!=null && function.getId()==id)
				return function;
		return null;
	}

	@Override
	public void reload() {
		
		// Global Resource List
		try {
			globalResourceListLock.writeLock().lock();
			globalResourceList = defaultSecurityLoaderRepository.getAllGlobalResources();
		} finally {
			globalResourceListLock.writeLock().unlock();
		}

		// Function List
		try {
			functionListLock.writeLock().lock();
			functionList = defaultSecurityLoaderRepository.getAllFunctions();
		} finally {
			functionListLock.writeLock().unlock();
		}

		// Function Resource List
		try {
			functionResourceListLock.writeLock().lock();
			functionResourceList = defaultSecurityLoaderRepository.getAllFunctionResources();
		} finally {
			functionResourceListLock.writeLock().unlock();
		}

		// Resource Role Map
		try {
			resourceRoleMapLock.writeLock().lock();
			resourceRoleMap = roleRepository.getAllResourceRoles();
		} finally {
			resourceRoleMapLock.writeLock().unlock();
		}

		// Role Function Map
		try {
			roleFunctionMapLock.writeLock().lock();
			roleFunctionMap = new HashMap<>();
		} finally {
			roleFunctionMapLock.writeLock().unlock();
		}
	}
}
