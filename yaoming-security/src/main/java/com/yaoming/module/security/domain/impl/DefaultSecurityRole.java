package com.yaoming.module.security.domain.impl;

import com.yaoming.module.security.domain.SecurityRole;

public class DefaultSecurityRole implements SecurityRole {
	private static final long serialVersionUID = 7652029081544301825L;
	
	private long id;
	private String name;
	private String note;

	public DefaultSecurityRole() {
	}

	public DefaultSecurityRole(String name) {
		this.id = -1;
		this.name = name;
	}
	
	public DefaultSecurityRole(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public DefaultSecurityRole(long id, String name, String note) {
		this(id, name);
		this.note = note;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNote() {
		return note;
	}

	public String getAuthority() {
		return getName();
	}

	public String getAttribute() {
		return getName();
	}

	public static class Factory {
	}
}