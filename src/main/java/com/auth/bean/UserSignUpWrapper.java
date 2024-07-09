package com.auth.bean;

import java.util.List;

import com.auth.entity.UserDetails;
import com.auth.entity.UserRoles;

public class UserSignUpWrapper {

	private UserDetails userDeatails;
	
	private List<UserRoles> userRoles;

	public UserDetails getUserDeatails() {
		return userDeatails;
	}

	public void setUserDeatails(UserDetails userDeatails) {
		this.userDeatails = userDeatails;
	}

	public List<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}
}
