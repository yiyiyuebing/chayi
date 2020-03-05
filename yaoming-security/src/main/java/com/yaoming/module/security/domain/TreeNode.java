package com.yaoming.module.security.domain;

public class TreeNode {
	private long id;
	private String note;
	public TreeNode(long id, String note) {
		this.id = id;
		this.note = note;
	}
	public long getId() {
		return id;
	}
	public String getNote() {
		return note;
	}
	public static class FunctionNode extends TreeNode{
		private long parentId;
		private String name;
		public FunctionNode(long id, long parentId, String name, String note) {
			super(id, note);
			this.parentId = parentId;
			this.name = name;
		}
		public long getParentId() {
			return parentId;
		}
		public String getName() {
			return name;
		}
	}
	
	public static class FunctionLeaf extends TreeNode{
		private long functionId;
		private String pattern;
		public FunctionLeaf(long id, long functionId, String pattern, String note) {
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
