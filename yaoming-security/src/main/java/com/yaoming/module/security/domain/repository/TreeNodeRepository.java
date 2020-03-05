package com.yaoming.module.security.domain.repository;

import java.util.List;

import com.yaoming.module.security.domain.TreeNode.FunctionLeaf;
import com.yaoming.module.security.domain.TreeNode.FunctionNode;

public interface TreeNodeRepository {
	public List<FunctionNode> getFunctionNodeList(long functionId);
	public List<FunctionLeaf> getFunctionLeafList(long functionId);
	public void saveFunctionNode(long oldId, FunctionNode node);
	public void deleteFunctionNode(long id);
	public void saveFunctionLeaf(long oldId, FunctionLeaf leaf);
	public void deleteFunctionLeaf(long id);
}
