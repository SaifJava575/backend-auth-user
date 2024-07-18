package com.auth.service.impl;

import java.util.List;

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
}
