package com.edu.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUser extends User{
	
	public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	private String fullname;
	public String getfullName() {
		return fullname;
	}
	public void setfullName(String fullname) {
		this.fullname = fullname;
	}
	
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
