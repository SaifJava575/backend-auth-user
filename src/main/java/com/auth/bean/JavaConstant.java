package com.auth.bean;

public class JavaConstant {

	public static final String CHECK_LOGIN_ID_EXISTS = "select new map(userId as userId) from UserDetails where loginId=?1";
    
	public static final String UPDATE_USER_DETAILS_ALL_BY_ID = "from UserDetails where userId=?1 and activeFlag=true ";

	public static final String USER_ROLE_BY_USER_ID = "select id.userId as userId,id.roleId as roleId,activeFlag as activeFlag from UserRoles where id.userId=?1 and id.roleId=?2 ";

	public static final String USER_CHANGE_PASSWD = "select userId as userId,loginId as loginId,userPwd as userPwd from UserDetails where loginId=?1 ";
	public static final String USER_UPDATE_PASSWD = "update Users set userPwd=?1,lastChangePasswordUpdatedOn=now() where UserDetails=?2 ";
   
	public static final String GET_USER_DETAILS = "select user_id as \"userId\",login_id as \"loginId\",user_pwd as \"userPwd\",CONCAT(first_name,'',last_name) as \"userName\" ,user_email as \"userEmail\",user_mobile_no as \"mobileNo\",active_flag as \"activeFlag\" from users_details where login_id=?1 ";


}
