package com.yaoming.module.security.dao.repositoryimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yaoming.module.security.dao.SecurityFunctionExtendMapper;
import com.yaoming.module.security.dao.SecurityFunctionResourcePatternExtendMapper;
import com.yaoming.module.security.dao.base.po.SecurityFunction;
import com.yaoming.module.security.dao.base.po.SecurityFunctionResourcePattern;
import com.yaoming.module.security.dao.po.SecurityFunctionExtendPo;
import com.yaoming.module.security.domain.TreeNode.FunctionLeaf;
import com.yaoming.module.security.domain.TreeNode.FunctionNode;
import com.yaoming.module.security.domain.repository.TreeNodeRepository;

@Repository
public class TreeNodeRepositoryImpl implements TreeNodeRepository {

	@Autowired private SecurityFunctionResourcePatternExtendMapper functionResourceDao;
	@Autowired private SecurityFunctionExtendMapper functionDao;
	
	@Override
	public List<FunctionNode> getFunctionNodeList(long functionId) {
		List<SecurityFunctionExtendPo> list = functionDao.selectByParentId(functionId);
		List<FunctionNode> result = new ArrayList<>();
		for (SecurityFunction sf : list)
			result.add(getDomainByPo(sf));
		return result;
	}

	@Override
	public List<FunctionLeaf> getFunctionLeafList(long functionId) {
		List<SecurityFunctionResourcePattern> list = functionResourceDao.selectByFunctionId(functionId);
		List<FunctionLeaf> result = new ArrayList<>();
		for (SecurityFunctionResourcePattern sf : list)
			result.add(getDomainByPo(sf));
		return result;
	}

	@Override
	public void saveFunctionNode(long oldId, FunctionNode node) {
		Assert.state(node.getId()>0, "功能ID必须大于0。");
		if(oldId<=0)
			functionDao.replace(getPoByDomain(node));
		else
			functionDao.updateByOldId(node.getId(), oldId, node.getParentId(), node.getName(), node.getNote());
	}

	@Override
	public void deleteFunctionNode(long id) {
		functionDao.deleteByPrimaryKey(id);
	}

	@Override
	public void saveFunctionLeaf(long oldId, FunctionLeaf leaf) {
		Assert.state(leaf.getId()>0, "资源ID必须大于0。");
		if(oldId<=0)
			functionResourceDao.replace(getPoByDomain(leaf));
		else
			functionResourceDao.updateByOldId(leaf.getId(), oldId, leaf.getFunctionId(), leaf.getPattern(), leaf.getNote());

	}

	@Override
	public void deleteFunctionLeaf(long id) {
		functionResourceDao.deleteByPrimaryKey(id);
	}

	private FunctionNode getDomainByPo(SecurityFunction po){
		return new FunctionNode(po.getId(), po.getParentId(), po.getName(), po.getName());
	}

	private FunctionLeaf getDomainByPo(SecurityFunctionResourcePattern po){
		return new FunctionLeaf(po.getId(), po.getFunctionId(), po.getPattern(), po.getNote());
	}
	
	private SecurityFunction getPoByDomain(FunctionNode domain){
		SecurityFunction po = new SecurityFunction();
		po.setId(domain.getId());
		po.setName(domain.getName());
		po.setParentId(domain.getParentId());
		po.setNote(domain.getNote());
		return po;
	}

	private SecurityFunctionResourcePattern getPoByDomain(FunctionLeaf domain){
		SecurityFunctionResourcePattern po = new SecurityFunctionResourcePattern();
		po.setId(domain.getId());
		po.setFunctionId(domain.getFunctionId());
		po.setPattern(domain.getPattern());
		po.setNote(domain.getNote());
		return po;
	}
}
