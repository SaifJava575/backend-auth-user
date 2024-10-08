package com.auth.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.bean.JavaConstant;
import com.auth.bean.SignUpSearchBean;
import com.auth.bean.Status;
import com.auth.bean.UserSignUpWrapper;
import com.auth.bean.UserValidation;
import com.auth.dao.IGenericDao;
import com.auth.entity.UserAndUserRolesPk;
import com.auth.entity.UserDetails;
import com.auth.entity.UserPasswordHistory;
import com.auth.entity.UserRoles;
import com.auth.entity.Users;
import com.auth.service.UserService;
import com.auth.utility.UtilProperty;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private IGenericDao iGenericDao;

	@Autowired
	private UtilProperty utility;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	@Transactional
	public Status saveUserDeatails(UserSignUpWrapper userSignUpWrapper) {
		try {
			String alreadyExists = "";
			if (userSignUpWrapper.getUserDeatails().getLoginId() != null) {
				List<Map<String, Object>> users = iGenericDao.executeDDLHQL(JavaConstant.CHECK_LOGIN_ID_EXISTS,
						new Object[] {
								userSignUpWrapper.getUserDeatails().getLoginId().toLowerCase().replaceAll(" ", "") });
				if (users.size() > 0) {
					alreadyExists = "loginIdExists";
					return new Status("205", alreadyExists, users.get(0).get("userId").toString());
				}

				if (alreadyExists.equals("")) {
					iGenericDao.save(userSignUpWrapper.getUserDeatails());
					for (UserRoles userRoles : userSignUpWrapper.getUserRoles()) {
						UserAndUserRolesPk userAndUserRolesPk = new UserAndUserRolesPk();
						userAndUserRolesPk.setUserId(userSignUpWrapper.getUserDeatails().getUserId());
						userAndUserRolesPk.setRoleId(userRoles.getId().getRoleId());
						userRoles.setId(userAndUserRolesPk);
						iGenericDao.save(userRoles);
					}
				}
				return new Status("200", "Sign Up Successfull",
						userSignUpWrapper.getUserDeatails().getUserId().toString());
			} else {
				alreadyExists = "didNotProvideLoginId";
				return new Status("206", "Please provide Login id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status("500", "Please provide Login id");
		}
	}

	@Override
	public void captchaRequest(HttpServletResponse response) {
		try {
			int width = 130;
			int height = 40;

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bufferedImage.createGraphics();
			Font font = new Font("Georgia", Font.BOLD, 18);
			g2d.setFont(font);
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHints(rh);

			GradientPaint gp = new GradientPaint(0, 0, Color.white, 0, height / 2, Color.white, true);
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, width, height);
			g2d.setColor(new Color(0, 0, 0));

			Integer captchaCode = 100000 + new Random().nextInt(800000);
			g2d.drawString(String.valueOf(captchaCode), 27, 27);
			g2d.dispose();
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();
			response.setHeader("captcha", encryptionPassword(String.valueOf(captchaCode)));

			ImageIO.write(bufferedImage, "png", os);
			os.close();
			response.flushBuffer();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	static String encryptionPassword(String password) {
		try {
			String encryptPassword = null;
			try {
				String secret = "$$CHALLENGE";
				Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
				sha256_HMAC.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
				encryptPassword = new String(Base64.encodeBase64(sha256_HMAC.doFinal(password.getBytes()), false));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			return encryptPassword;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	@Transactional
	public String updateUserDetails(UserSignUpWrapper userDetails) {
		String updateStatus = "";
		try {
			UserDetails userInfo = userDetails.getUserDeatails();
			List<UserRoles> userRoles = userDetails.getUserRoles();
			List<Users> fetchMasterUserDetails = iGenericDao.executeDDLHQL(JavaConstant.UPDATE_USER_DETAILS_ALL_BY_ID,
					new Object[] { userInfo.getUserId() });

			if (fetchMasterUserDetails.size() > 0) {

				Users fetchMasterUserDetailsInfo = fetchMasterUserDetails.get(0);
				utility.copyNonNullProperties(userDetails, fetchMasterUserDetailsInfo);
				iGenericDao.update(fetchMasterUserDetailsInfo);

				for (UserRoles roles : userRoles) {
					List<?> userRolesDetails = iGenericDao.executeDDLHQL(JavaConstant.USER_ROLE_BY_USER_ID,
							new Object[] { userInfo.getUserId(), roles.getId().getRoleId() });
					Integer roleId = 0;
					if (userRolesDetails != null && userRolesDetails.size() > 0) {
						Object[] value = (Object[]) userRolesDetails.get(0);
						roleId = (Integer) value[1];
						utility.copyNonNullProperties(roles, value);
						iGenericDao.update(roles);
					} else if (userRolesDetails.size() == 0) {
						if (roles.getActiveFlag() != null && roles.getActiveFlag() == true
								&& roleId != roles.getId().getRoleId())
							iGenericDao.save(roles);
					}
				}

				logger.info("updateUserDetails updated: '{}'" + userDetails.toString());
				updateStatus = "updatedSucessFully";
				logger.info(updateStatus + " " + userInfo.getFirstName() + " " + userInfo.getLastName());
			} else {
				logger.warn("updateUserDetails: '{}' " + userInfo.getFirstName() + " " + userInfo.getLastName()
						+ " and userId : " + userInfo.getUserId() + " not available!");
				updateStatus = "userDetailsNotExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updateStatus;
	}
	
	@Override
	public String getChangePassword(SignUpSearchBean signUpSearchBean) {
		List<?> list = null;
		String resultflag = "";
		try {
			if (signUpSearchBean.getPasswordType() != null) {
				if (signUpSearchBean.getPasswordType().equals("C")) {
					list = iGenericDao.executeDDLHQL(JavaConstant.USER_CHANGE_PASSWD,
							new Object[] { signUpSearchBean.getLoginId() });
					if (list != null && list.size() > 0) {
						Object[] value = (Object[]) list.get(0);
						Integer userId = (Integer) value[0];
						String userPwd = (String) value[2];
						if (!userPwd.equals(signUpSearchBean.getNewPassword())
								&& userPwd.equals(signUpSearchBean.getOldPassword())) {
							iGenericDao.executeDMLHQL(JavaConstant.USER_UPDATE_PASSWD,
									new Object[] { signUpSearchBean.getNewPassword(), userId });
							UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
							userPasswordHistory.setUserId(userId);
							userPasswordHistory.setOldPassword(userPwd);
							iGenericDao.save(userPasswordHistory);
							resultflag = "200";
						} else
							resultflag = "201";
					} else
						resultflag = "202";
				} else if (signUpSearchBean.getPasswordType().equals("F")) {
					list = iGenericDao.executeDDLHQL(JavaConstant.USER_CHANGE_PASSWD,
							new Object[] { signUpSearchBean.getLoginId() });

					if (list == null || list.size() == 0) {
						return "202";
					}

					if (list != null && list.size() > 0 && signUpSearchBean.getNewPassword() != null) {
						Object[] value = (Object[]) list.get(0);
						Integer userId = (Integer) value[0];
						String userPwd = (String) value[2];
						iGenericDao.executeDMLHQL(JavaConstant.USER_UPDATE_PASSWD,
								new Object[] { signUpSearchBean.getNewPassword(), userId });
						UserPasswordHistory userPasswordHistory = new UserPasswordHistory();
						userPasswordHistory.setUserId(userId);
						userPasswordHistory.setOldPassword(userPwd);
						iGenericDao.save(userPasswordHistory);
						resultflag = "203";
						return resultflag;
					} else if (list != null && list.size() > 0 && signUpSearchBean.getNewPassword() == null)
						return "206";
					else
						resultflag = "201";
				}
			} else
				resultflag = "204";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultflag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject userValidationCheck(UserValidation userDetails) {
		JSONObject json = new JSONObject();
		try {
			List<?> details = iGenericDao.executeDDLSQL(JavaConstant.GET_USER_DETAILS,
					new Object[] { userDetails.getLoginId() });
			if (details.size() > 0) {
				Map<String, Object> map = (Map<String, Object>) details.get(0);
				Integer userId = (Integer) map.get("userId");
				String userName = map.get("userName").toString();
				String userPwd = map.get("userPwd").toString();
				String userEmail = map.get("userEmail").toString();
				String mobileNo = map.get("mobileNo").toString();
				Boolean activeFlag = (Boolean) map.get("activeFlag");
				if (userPwd != null && !userPwd.equals(userDetails.getPassword())) {
					json.put("statusCode", "400");
					json.put("message", "Invalid password");
					json.put("userId", userDetails.getLoginId());
					return json;
				}
				json.put("statusCode", "200");
				json.put("message", "Valid Credential");
				json.put("loginId", userDetails.getLoginId());
				json.put("userId",userId);
				json.put("userName",userName);
				json.put("userPwd",userPwd);
				json.put("userEmail",userEmail);
				json.put("mobileNo",mobileNo);
				json.put("activeFlag",activeFlag);
				
			} else {
				json.put("statusCode", "400");
				json.put("message", "Invalid loginId");
				json.put("loginId", userDetails.getLoginId());
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
