package com.yaoming.module.security.domain.impl;

import java.util.ArrayList;
import java.util.List;

import com.yaoming.module.security.domain.SecurityFunction;
import com.yaoming.module.security.domain.SecurityFunctionTree;

public class DefaultSecurityFunctionTree extends DefaultSecurityFunction implements SecurityFunctionTree {
	
	private List<SecurityFunctionTree> children = new ArrayList<>();

	public DefaultSecurityFunctionTree(){
	}

	public DefaultSecurityFunctionTree(SecurityFunction f) {
		super(f.getId(), f.getParentId(), f.getName(), f.getUrl(), f.getIndex(), f.getNote(), f.getRoleIds());
	}
	
	@Override
	public List<SecurityFunctionTree> getChildren() {
		return children;
	}

	@Override
	public void addChild(SecurityFunctionTree child) {
		if(child==null)
			return;
		for (SecurityFunctionTree c : children)
			if(c!=null && c.getId()==child.getId())
				return;
		children.add(child);
	}

	@Override
	public void addChildren(List<SecurityFunctionTree> children) {
		if(children==null)
			return;
		for (SecurityFunctionTree child : children)
			this.addChild(child);
	}

	@Override
	public int compareTo(SecurityFunctionTree o) {
		int i = this.getIndex()-o.getIndex();
		if(i!=0)
			return i;
		return (int) (this.getId()-o.getId());
	}
	
}
