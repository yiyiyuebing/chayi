package com.yaoming.module.security.service.vo;

import com.yaoming.module.security.domain.SecurityResource;

public class SecurityGlobalResourcePatternVO {
	
    private Long id;
    private String pattern;
    private String authority;
    private String note;
    
    public SecurityGlobalResourcePatternVO(SecurityResource domain){
    	this(domain.getId(), domain.getPattern(), domain.getAuthority(), domain.getNote());
    }
    
	public SecurityGlobalResourcePatternVO(Long id, String pattern, String authority, String note) {
		this.id = id;
		this.pattern = pattern;
		this.authority = authority;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public String getPattern() {
		return pattern;
	}

	public String getAuthority() {
		return authority;
	}

	public String getNote() {
		return note;
	}
    
}
