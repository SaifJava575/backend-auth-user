package com.auth.service;

import org.json.simple.JSONObject;

import com.auth.bean.SignUpSearchBean;
import com.auth.bean.Status;
import com.auth.bean.UserSignUpWrapper;
import com.auth.bean.UserValidation;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	Status saveUserDeatails(UserSignUpWrapper userSignUpWrapper);

	void captchaRequest(HttpServletResponse response);

	String updateUserDetails(UserSignUpWrapper userDetails);

	String getChangePassword(SignUpSearchBean signUpSearchBean);

	JSONObject userValidationCheck(UserValidation userDetails);

}
