package com.auth.service;

import com.auth.bean.Status;
import com.auth.bean.UserSignUpWrapper;

import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	Status saveUserDeatails(UserSignUpWrapper userSignUpWrapper);

	void captchaRequest(HttpServletResponse response);

}
