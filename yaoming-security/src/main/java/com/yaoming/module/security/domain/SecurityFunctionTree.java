package com.yaoming.module.security.domain;

import java.util.List;

public interface SecurityFunctionTree extends SecurityFunction, Comparable<SecurityFunctionTree> {

	public List<SecurityFunctionTree> getChildren();
	
	public void addChild(SecurityFunctionTree child);
	
	public void addChildren(List<SecurityFunctionTree> children);
}
