package com.yaoming.module.security.service.vo;

import com.yaoming.module.security.domain.SecurityRole;

public class SecurityRoleVO {
	private long id;
	private String name;
	private String note;
	public SecurityRoleVO(SecurityRole sr) {
		this(sr.getId(), sr.getName(), sr.getNote());
	}
	public SecurityRoleVO(long id, String name, String note) {
		this.id = id;
		this.name = name;
		this.note = note;
	}
	public String getId() {
		return id+"";
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
