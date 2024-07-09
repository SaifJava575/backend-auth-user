package com.auth.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserAndUserRolesPk implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Column(name="user_id")
	private Integer userId; 
	
	@Column(name="role_id")
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}
