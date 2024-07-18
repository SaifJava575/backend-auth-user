package com.auth.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.IUnstopServiceChallange;

@RestController
@RequestMapping("/unstop")
public class UnstopRestApiController {

	@Autowired
	private IUnstopServiceChallange unstopService;
	
	@PostMapping("/goodPuddingApproach")
    public int[] goodPuddingApproach(@RequestBody List<Integer> arr) {
        return unstopService.goodPuddingApproach(arr);
    }
	
	 @GetMapping("/diagonalSum")
	    public int diagonalSum(@RequestParam int n) {
	        return unstopService.diagonalSum(n);
	    }
	 
	  @GetMapping("/movement")
	    public String movement(@RequestParam String moves) {
	        return unstopService.movement(moves);
	    }
}
