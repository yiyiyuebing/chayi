package com.yaoming.module.security.domain.impl;

import com.yaoming.module.security.domain.SecurityResource;

public class DefaultSecurityResource implements SecurityResource {

	private long id;
	private String pattern;
	private String note;
	private String authority;
	
	public DefaultSecurityResource() {
	}
	
	public DefaultSecurityResource(long id) {
		this.id = id;
	}
	public DefaultSecurityResource(long id, String pattern) {
		this(id);
		this.pattern = pattern;
	}

	public DefaultSecurityResource(long id, String pattern, String authority) {
		this(id, pattern);
		this.authority = authority;
	}

	public DefaultSecurityResource(long id, String pattern, String note, String authority) {
		this(id, pattern, authority);
		this.id = id;
		this.pattern = pattern;
		this.note = note;
		this.authority = authority;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getPattern() {
		return pattern;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public String getNote() {
		return note;
	}

}
