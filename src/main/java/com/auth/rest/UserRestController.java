package com.auth.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.bean.Status;
import com.auth.bean.UserSignUpWrapper;
import com.auth.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/signUp")
	public Status saveUserDeatails(@RequestBody UserSignUpWrapper userSignUpWrapper) {
		return userService.saveUserDeatails(userSignUpWrapper);
	}
	
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public @ResponseBody void captchaRequest(HttpServletResponse response) {
		try {
			userService.captchaRequest(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
