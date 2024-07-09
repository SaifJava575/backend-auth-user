package com.auth.bean;

public class JavaConstant {

	public static final String CHECK_LOGIN_ID_EXISTS = "select new map(userId as userId) from Users where loginId=?1";

}
