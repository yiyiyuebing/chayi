package com.yaoming.module.security.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.yaoming.module.security.domain.SecurityFunctionTree;
import com.yaoming.module.security.domain.TreeNode.FunctionLeaf;
import com.yaoming.module.security.domain.TreeNode.FunctionNode;

public abstract class SecurityTreeNodeVO {
	private long id;
	private String note;
	public SecurityTreeNodeVO(){
	}
	public SecurityTreeNodeVO(long id, String note) {
		this.id = id;
		this.note = note;
	}
	public long getId() {
		return id;
	}
	public String getNote() {
		return note;
	}
	public static class SecurityFunctionTreeNodeVO extends SecurityTreeNodeVO {
		private long parentId;
		private String name;
		private String url;
		private int index;
		private List<Long> roleIds;
		private List<SecurityFunctionTreeNodeVO> children;
		public SecurityFunctionTreeNodeVO(FunctionNode functionNode) {
			this(functionNode.getId(), functionNode.getParentId(), functionNode.getName(), functionNode.getNote());
			children = new ArrayList<>();
		}
		public SecurityFunctionTreeNodeVO(long id, long parentId, String name, String note) {
			super(id, note);
			this.parentId = parentId;
			this.name = name;
		}
		public static SecurityFunctionTreeNodeVO create(SecurityFunctionTree tree) {
			if(tree==null)
				return null;
			SecurityFunctionTreeNodeVO vo = new SecurityFunctionTreeNodeVO(tree.getId(), tree.getParentId(), 
					tree.getName(), tree.getUrl(), tree.getIndex(), tree.getNote(), tree.getRoleIds());
			for (SecurityFunctionTree child : tree.getChildren())
				vo.addChildren(create(child));
			return vo;
		}
		
		public SecurityFunctionTreeNodeVO(long id, long parentId, String name, String url, int index, String note,
				List<Long> roleIds) {
			this(id, parentId, name, note);
			this.url = url;
			this.index = index;
			this.roleIds = roleIds;
			this.children = new ArrayList<>();
		}
		
		public long getParentId() {
			return parentId;
		}
		public String getName() {
			return name;
		}
		public String getUrl() {
			return url;
		}
		public int getIndex() {
			return index;
		}
		public List<Long> getRoleIds() {
			return roleIds;
		}
		public List<SecurityFunctionTreeNodeVO> getChildren() {
			return children;
		}
		public void addChildren(SecurityFunctionTreeNodeVO child) {
			this.children.add(child);
		}
		public void addAllChildren(List<SecurityFunctionTreeNodeVO> children) {
			this.children.addAll(children);
		}
	}
	public static class SecurityResourcePatternLeafVO extends SecurityTreeNodeVO {
		private long functionId;
		private String pattern;
		public SecurityResourcePatternLeafVO(FunctionLeaf functionLeaf) {
			this(functionLeaf.getId(), functionLeaf.getFunctionId(), functionLeaf.getPattern(), functionLeaf.getNote());
		}
		public SecurityResourcePatternLeafVO(long id, long functionId, String pattern, String note) {
			super(id, note);
			this.functionId = functionId;
			this.pattern = pattern;
		}
		public long getFunctionId() {
			return functionId;
		}
		public String getPattern() {
			return pattern;
		}
	}
}
