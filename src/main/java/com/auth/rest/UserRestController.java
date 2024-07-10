package com.auth.rest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.bean.Response;
import com.auth.bean.SignUpSearchBean;
import com.auth.bean.Status;
import com.auth.bean.UserSignUpWrapper;
import com.auth.bean.UserValidation;
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
	
	@SuppressWarnings("unchecked")
	@PostMapping("/signIn")
	public JSONObject userValidationCheck(@RequestBody UserValidation userDetails) {
		JSONObject json = new JSONObject();
		try {
			if (userDetails.getLoginId() == null || userDetails.getLoginId().equals("")) {
				json.put("message", "please provide userName");
				return json;
			}
			if (userDetails.getPassword() == null || userDetails.getPassword().equals("")) {
				json.put("message", "please provide Password");
				return json;
			}
			return userService.userValidationCheck(userDetails);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", HttpStatus.EXPECTATION_FAILED);
			return json;
		}
	}
	
	@RequestMapping(value = "/updateUserDetails", method = RequestMethod.POST)
	public @ResponseBody Response<?> updateUserDetails(@RequestBody UserSignUpWrapper userDetails) {
		String savedStatus = "";
		try {
			savedStatus = userService.updateUserDetails(userDetails);
			if (savedStatus == "userDetailsNotExist") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_NOT_FOUND),
						"Users not exist");
			} else if (savedStatus == "updatedSucessFully") {
				return new Response<>(String.valueOf(HttpServletResponse.SC_OK), savedStatus);
			} else {
				return new Response<>(String.valueOf(HttpServletResponse.SC_BAD_REQUEST),
						"error occured while updating Users");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<>(String.valueOf(HttpServletResponse.SC_EXPECTATION_FAILED), e.toString());
		}
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody Status getChangePassword(@RequestBody SignUpSearchBean signUpSearchBean) {
		try {
			String statusCode=userService.getChangePassword(signUpSearchBean);
			if(statusCode!=null && statusCode.equals("200"))
				return new Status("200", "SuccessFully Change Password");
			else if(statusCode!=null && statusCode.equals("201"))
				return new Status("201", "Old password and New Password Should not be same");
			else if(statusCode!=null && statusCode.equals("202"))
				return new Status("202", "Your login Id is not Exist");
			else if(statusCode!=null && statusCode.equals("203"))
				return new Status("203", "SuccessFully Updated Password");
			else if(statusCode!=null && statusCode.equals("204"))
				return new Status("204", "Password type should not be null");
			else if(statusCode!=null && statusCode.equals("206"))
				return new Status("200", "Login Id Exists");
			else
				return new Status("205", "FAILED");

		} catch (Exception e) {
			e.printStackTrace();
			return new Status("500", "Interna; Server Error");
		}
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
