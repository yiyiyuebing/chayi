package com.yaoming.module.security.domain;

import java.util.List;

public interface SecurityFunction {
	
	public static final long ROOT_PARENT_ID = 0;
	
	public long getId();
	public long getParentId();
	public String getName();
	public String getUrl();
	public int getIndex();
	public String getNote();
	public List<Long> getRoleIds();
}
