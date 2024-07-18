package com.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.bean.JavaConstant;
import com.auth.dao.IGenericDao;
import com.auth.service.ILeetCodeSqlQueryService;

@Service
public class LeetCodeSqlQueryServiceImpl implements ILeetCodeSqlQueryService {

	@Autowired
	private IGenericDao iGenericDao;

	@Override
	public List<?> qualityAndPercentage() {
		List<?> qualityPercentage = null;
		try {
			qualityPercentage = iGenericDao.executeDDLSQL(JavaConstant.QUERY_QUALITY_AND_PERCENTAGE, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qualityPercentage;
	}
	
	@Override
	public List<?> monthlyTrasaction() {
		List<?> transaction = null;
		try {
			transaction = iGenericDao.executeDDLSQL(JavaConstant.MONTHLY_TRANSACTION_API, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transaction;
	}
	
	@Override
	public List<?> immediateFoodDelivery() {
		List<?> immediateFoodelivery = null;
		try {
			immediateFoodelivery = iGenericDao.executeDDLSQL(JavaConstant.IMMEDIATE_FOOD_DELIVERY, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return immediateFoodelivery;
	}
}
