package com.auth.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	  
	  @PostMapping("/canBeSegmented")
	    public boolean canBeSegmented(@RequestParam String s, @RequestBody List<String> dictionary) {
	        Set<String> dictSet = new HashSet<>(dictionary);
	        return unstopService.canBeSegmented(s, dictSet);
	    }
	  
	  @PostMapping("/findTotalSum")
	    public int findTotalSum(@RequestParam String a, @RequestParam String b) {
	        return unstopService.findTotalSum(a, b);
	    }
	  
	  @GetMapping("/reverseFromLastOccurrence")
	    public String reverseFromLastOccurrence(
	        @RequestParam String s,
	        @RequestParam char ch) {
	        return unstopService.reverseFromLastOccurrence(s, ch);
	    }
	  
	  @GetMapping("/boxAbPattern")
	    public String boxAbPattern(@RequestParam String abPattern) {
	        return unstopService.boxAbPattern(abPattern);
	    }
	  
	  @PostMapping("/repeatedNumber")
	    public int getRepeatedNumber(@RequestBody int[] arr) {
	        return unstopService.getRepeatedNumber(arr, arr.length);
	    }
	  
	  @PostMapping("/maxAbsDifference")
	    public int getMaxAbsDifference(@RequestBody int[][] queries) {
	        return unstopService.computeMaxAbsDifference(queries);
	    }
}
