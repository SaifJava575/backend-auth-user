package com.auth.bean;

public class JavaConstant {

	public static final String CHECK_LOGIN_ID_EXISTS = "select new map(userId as userId) from UserDetails where loginId=?1";
    
	public static final String UPDATE_USER_DETAILS_ALL_BY_ID = "from UserDetails where userId=?1 and activeFlag=true ";

	public static final String USER_ROLE_BY_USER_ID = "select id.userId as userId,id.roleId as roleId,activeFlag as activeFlag from UserRoles where id.userId=?1 and id.roleId=?2 ";

	public static final String USER_CHANGE_PASSWD = "select userId as userId,loginId as loginId,userPwd as userPwd from UserDetails where loginId=?1 ";
	public static final String USER_UPDATE_PASSWD = "update Users set userPwd=?1,lastChangePasswordUpdatedOn=now() where UserDetails=?2 ";
   
	public static final String GET_USER_DETAILS = "select user_id as \"userId\",login_id as \"loginId\",user_pwd as \"userPwd\",CONCAT(first_name,'',last_name) as \"userName\" ,user_email as \"userEmail\",user_mobile_no as \"mobileNo\",active_flag as \"activeFlag\" from users_details where login_id=?1 ";

	public static final String QUERY_QUALITY_AND_PERCENTAGE ="SELECT query_name as \"quearyName\",ROUND(SUM(rating::numeric / position) / COUNT(result), 2) as \"quality\",ROUND(100. * COUNT(rating) FILTER ( WHERE rating < 3 ) / COUNT(rating), 2) as \"poorQueryPercentage\" FROM leetcode.Queries WHERE query_name IS NOT NULL GROUP BY query_name ";
	
	public static final String MONTHLY_TRANSACTION_API ="SELECT TO_CHAR(trans_date, 'YYYY-MM') AS month,country,COUNT(*) AS \"transCount\", COUNT(state) FILTER (WHERE state = 'approved') AS \"approvedCount\", SUM(amount) AS \"transTotalAmount\",COALESCE(SUM(amount) FILTER (WHERE state = 'approved'), 0) AS \"approvedTotalAmount\" FROM leetcode.Transactions GROUP BY month, country ";
	
	public static final String IMMEDIATE_FOOD_DELIVERY ="SELECT ROUND(AVG(CASE WHEN order_date = customer_pref_delivery_date THEN 1 ELSE 0 END)*100, 2) AS \"immediatePercentage\" FROM leetcode.Delivery WHERE (customer_id, order_date) IN (SELECT customer_id,MIN(order_date) FROM leetcode.Delivery GROUP BY customer_id) ";
	
	public static final String GAME_PLAY_ANALYSIS ="select round (count(*)::numeric / (select count (distinct player_id) from leetcode.activity) , 2) as \"fraction\" from leetcode.activity where (player_id , event_date) in (select player_id , min(event_date) + 1 firstLog from leetcode.activity  group by 1 )";
	
	public static final String NUMBER_OF_UNIQUE_SUBJECT="SELECT teacher_id as \"teacherId\", COUNT(DISTINCT subject_id) AS \"count\" FROM leetcode.Teacher GROUP BY 1 ";
	public static final String USER_ACTIVITY_PAST_30_DAYS="select activity_date as day ,Count(distinct user_id)  as \"activeUsers\" from leetcode.user_Activity where activity_date between '2019-06-28' and '2019-07-27' group by activity_date ";
}
