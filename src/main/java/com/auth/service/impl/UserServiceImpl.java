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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.bean.JavaConstant;
import com.auth.bean.Status;
import com.auth.bean.UserSignUpWrapper;
import com.auth.dao.IGenericDao;
import com.auth.entity.UserAndUserRolesPk;
import com.auth.entity.UserRoles;
import com.auth.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private IGenericDao iGenericDao;

	
	@Override
	public Status saveUserDeatails(UserSignUpWrapper userSignUpWrapper) {
		try {
			String alreadyExists = "";
			if (userSignUpWrapper.getUserDeatails().getLoginId() != null) {
       List<Map<String,Object>> users = iGenericDao.executeDDLHQL(JavaConstant.CHECK_LOGIN_ID_EXISTS,
				new Object[] { userSignUpWrapper.getUserDeatails().getLoginId().toLowerCase().replaceAll(" ", "") });  		   
				if (users.size() > 0) {
					alreadyExists = "loginIdExists";
					return new Status("205",alreadyExists,users.get(0).get("userId").toString());
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
				return new Status("200","Sign Up Successfull",userSignUpWrapper.getUserDeatails().getUserId().toString());			
			} else {
				alreadyExists = "didNotProvideLoginId";
				return new Status("206","Please provide Login id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status("500","Please provide Login id");
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
}
