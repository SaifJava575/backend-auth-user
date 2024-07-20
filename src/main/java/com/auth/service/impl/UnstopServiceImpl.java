package com.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.auth.service.IUnstopServiceChallange;

@Service
public class UnstopServiceImpl implements IUnstopServiceChallange {

	@Override
	public int[] goodPuddingApproach(List<Integer> arr) {
		int response[] = new int[arr.size()];
		try {
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i) % 10 == 0)
					response[i] = 0;
				else
					response[i] = 1;
			}
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings("null")
	@Override
	public int diagonalSum(int n) {
		try {
			if (n > 0 && n % 2 == 0)
				return n * 2;
			else if (n > 0 && n % 2 == 1)
				return n * 2 - 1;
			else if (n < 0 && n % 2 == 0)
				return n * 2;
			else if (n < 0 && n != 0)
				return n * 2 + 1;
			else
				return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return (Integer) null;
		}
	}

	@Override
	public String movement(String moves) {
		String str = "";
		try {
			int x = 0, y = 0;
			for (char move : moves.toCharArray()) {
				switch (move) {
				case 'R':
					x++;
					break;
				case 'L':
					x--;
					break;
				case 'U':
					y++;
					break;
				case 'D':
					y--;
					break;
				}
			}
			if (x == 0 && y == 0) {
				str = "YES";
			} else {
				str = "NO";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public boolean canBeSegmented(String s, Set<String> dictSet) {
		try {
			int length = s.length();
			boolean[] dp = new boolean[length + 1];
			dp[0] = true;

			for (int i = 1; i <= length; i++) {
				for (int j = 0; j < i; j++) {
					if (dp[j] && dictSet.contains(s.substring(j, i))) {
						dp[i] = true;
						break;
					}
				}
			}

			return dp[length];
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int findTotalSum(String a, String b) {
		int totalSum = 0;
		try {
			int aLength = a.length();
			int bLength = b.length();
			// Iterate through all contiguous substrings of b of length aLength
			for (int i = 0; i <= bLength - aLength; i++) {
				String substring = b.substring(i, i + aLength);
				totalSum += countDifferences(a, substring);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalSum;
	}

	private static int countDifferences(String a, String bSubstring) {
		int differences = 0;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != bSubstring.charAt(i)) {
				differences++;
			}
		}
		return differences;
	}

	@Override
	public String reverseFromLastOccurrence(String s, char ch) {
		String response = null;
		try {
			int idx = s.lastIndexOf(ch);

			if (idx == -1) {
				return s;
			}

			String prefix = s.substring(0, idx);
			String suffix = new StringBuilder(s.substring(idx)).reverse().toString();

			response = prefix + suffix;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String boxAbPattern(String abPattern) {
		try {
			char arr[] = abPattern.toCharArray();
			int count = 0;
			for (int i = 1; i < arr.length; i++) {
				if (arr[i - 1] == arr[i])
					continue;
				else if (arr[i - 1] != arr[i]) {
					count++;
				}
			}
			if (count > 1)
				return "NO";

			return "YES";
		} catch (Exception e) {
			e.printStackTrace();
			return "Exception";
		}
	}
	
	@Override
	public int getRepeatedNumber(int[] arr, int length) {
		int repetedNumber=0;
		try {
			Map<Integer,Integer> map=new HashMap<>();
			for(int i=0;i<arr.length;i++) {
				map.put(arr[i], map.getOrDefault(arr[i], 0)+1);
			}
			for(Map.Entry<Integer,Integer> res:map.entrySet()) {
				if(res.getValue()>1) {			
					repetedNumber=Math.max(repetedNumber, res.getKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repetedNumber;
	}
	
	@Override
	public int computeMaxAbsDifference(int[][] queries) {
		Map<Integer, Integer> frequencyMap = new HashMap<>();
		int result=0;
		try {
			for (int[] query : queries) {
				int count = query[0];
				int number = query[1];
				frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + count);
				result=calculateMaxAbsDifference(frequencyMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static int calculateMaxAbsDifference(Map<Integer, Integer> frequencyMap) {
		TreeMap<Integer, Integer> freqCountMap = new TreeMap<>();

		for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
			int number = entry.getKey();
			int freq = entry.getValue();
			freqCountMap.put(freq, number);
		}

		// If there are less than 2 distinct frequencies, the output should be 0
		if (freqCountMap.size() < 2) {
			return 0;
		} else {
			// Getting the highest and lowest frequencies
			int minFreq = freqCountMap.firstKey();
			int maxFreq = freqCountMap.lastKey();

			// Calculating the absolute difference between numbers with max and min
			// frequencies
			int minNumber = freqCountMap.get(minFreq);
			int maxNumber = freqCountMap.get(maxFreq);

			return Math.abs(maxNumber - minNumber);
		}
	}
}
