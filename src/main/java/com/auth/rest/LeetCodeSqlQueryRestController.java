package com.auth.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.ILeetCodeSqlQueryService;

@CrossOrigin(value = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class LeetCodeSqlQueryRestController {

	@Autowired
	private ILeetCodeSqlQueryService leetCodeService;

	@GetMapping("/qualityAndPercentage")
	public @ResponseBody List<?> qualityAndPercentage() {
		List<?> response = null;
		try {
			response = leetCodeService.qualityAndPercentage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	@GetMapping("/monthlyTrasaction")
	public @ResponseBody List<?> monthlyTrasaction() {
		List<?> response = null;
		try {
			response = leetCodeService.monthlyTrasaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/immediateFoodDelivery")
	public @ResponseBody List<?> immediateFoodDelivery() {
		List<?> response = null;
		try {
			response = leetCodeService.immediateFoodDelivery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/gamePlayAnalysis")
	public @ResponseBody List<?> gamePlayAnalysis() {
		List<?> response = null;
		try {
			response = leetCodeService.gamePlayAnalysis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/numberOfUniqueSubject")
	public @ResponseBody List<?> numberOfUniqueSubject() {
		List<?> response = null;
		try {
			response = leetCodeService.numberOfUniqueSubject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/userActivePast30Days")
	public @ResponseBody List<?> userActivePast30Days() {
		List<?> response = null;
		try {
			response = leetCodeService.userActivePast30Days();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}



}
