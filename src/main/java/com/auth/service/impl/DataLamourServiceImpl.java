package com.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.bean.JavaConstant;
import com.auth.dao.IGenericDao;
import com.auth.service.IDataLamourService;

@Service
public class DataLamourServiceImpl implements IDataLamourService {
	
	@Autowired
	private IGenericDao iGenericDao;
	
	@Override
	public List<?> tweetDataCount() {
		List<?> tweetDataCount = null;
		try {
			tweetDataCount = iGenericDao.executeDDLSQL(JavaConstant.TWEETS_DATA_COUNT, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tweetDataCount;
	}
	
	@Override
	public List<?> dataScienceSkill() {
		List<?> tweetDataCount = null;
		try {
			tweetDataCount = iGenericDao.executeDDLSQL(JavaConstant.DATA_SCIENCE_SKILL, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tweetDataCount;
	}
	
	@Override
	public List<?> pagesNoLike() {
		List<?> pagesNoLike = null;
		try {
			pagesNoLike = iGenericDao.executeDDLSQL(JavaConstant.PAGE_NO_LIKES, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagesNoLike;
	}

}
