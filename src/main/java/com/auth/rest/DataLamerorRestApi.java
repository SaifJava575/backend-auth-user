package com.auth.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.IDataLamourService;

@CrossOrigin(value = "http://localhost:4200")
@RestController
public class DataLamerorRestApi {

	@Autowired
	private IDataLamourService iDataLamourService;
	
	@GetMapping("/tweetDataCount")
	public @ResponseBody List<?> tweetDataCount() {
		List<?> response = null;
		try {
			response = iDataLamourService.tweetDataCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/dataScienceSkill")
	public @ResponseBody List<?> dataScienceSkill() {
		List<?> response = null;
		try {
			response = iDataLamourService.dataScienceSkill();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@GetMapping("/pagesNoLike")
	public @ResponseBody List<?> pagesNoLike() {
		List<?> response = null;
		try {
			response = iDataLamourService.pagesNoLike();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
