package com.yaoming.module.security.domain.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaoming.common.util.StringUtil;
import com.yaoming.module.security.domain.SecurityFunction;

public class DefaultSecurityFunction implements SecurityFunction {

	private Logger logger = LoggerFactory.getLogger(DefaultSecurityFunction.class);
	
	private long id;
	private long parentId;
	private String name;
	private String url;
	private int index;
	private String note;
	private List<Long> roleIds;
	public DefaultSecurityFunction() {
	}
	public DefaultSecurityFunction(long id, long parentId, String name, String url, int index, String note) {	
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.url = url;
		this.index = index;
		this.note = note;
	}
	public DefaultSecurityFunction(long id, long parentId, String name, String url, int index, String note, String roleIds) {
		this(id, parentId, name, url, index, note);
		this.setRoleIds(roleIds);
	}
	public DefaultSecurityFunction(long id, long parentId, String name, String url, int index, String note, List<Long> roleIds) {
		this(id, parentId, name, url, index, note);
		this.setRoleIds(roleIds);
	}
	@Override
	public long getId() {
		return id;
	}
	@Override
	public long getParentId() {
		return parentId;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getUrl() {
		return url;
	}
	@Override
	public int getIndex() {
		return index;
	}
	@Override
	public String getNote() {
		return note;
	}
	@Override
	public List<Long> getRoleIds() {
		return roleIds;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = new ArrayList<>();
		if(StringUtil.isEmpty(roleIds))
			return;
		String[] tempIds = roleIds.split(",");
		try{
			for (String tempId : tempIds)
				this.roleIds.add(Long.parseLong(tempId));
		} catch(NumberFormatException e) {
			this.roleIds = new ArrayList<>();
			logger.error("init roles failed. ", e);
		}
	}
}
