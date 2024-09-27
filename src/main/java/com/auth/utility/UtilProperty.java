package com.auth.utility;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;

import com.auth.bean.SendViaEmailModel;

import jakarta.mail.internet.MimeMessage;

@Component
public class UtilProperty {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	

	public void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
		
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	
	@SuppressWarnings("deprecation")
	public String sendEmails(SendViaEmailModel transMailQueueSender) {
		System.out.println(transMailQueueSender);
				String resultFlag = "N";
				try {
					if (!doesMailComesUnderRediffGroup(Objects.toString(transMailQueueSender.getToMail(), ""),
							Objects.toString(transMailQueueSender.getCcEmail(), ""), Objects.toString(transMailQueueSender.getBccEmail(), ""))) {
						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message,
								MimeMessageHelper.MULTIPART_MODE_RELATED);
						if (!StringUtils.isEmpty(transMailQueueSender.getToMail()))
							helper.setTo(transMailQueueSender.getToMail().split(","));
						if (!StringUtils.isEmpty(transMailQueueSender.getCcEmail()))
							helper.setCc(transMailQueueSender.getCcEmail().split(","));
						if (!StringUtils.isEmpty(transMailQueueSender.getBccEmail()))
							helper.setBcc(transMailQueueSender.getBccEmail().split(","));
						if (!StringUtils.isEmpty(transMailQueueSender.getSubject()))
							helper.setSubject(transMailQueueSender.getSubject());
						if (!StringUtils.isEmpty(transMailQueueSender.getMailContent()))
							helper.setText(transMailQueueSender.getMailContent(), true);
						helper.setFrom("itlearn575@gmail.com");
						mailSender.send(message);
						resultFlag = "Y";
					} else {
						resultFlag="Y";
					}
				} catch (Exception e) {
					e.printStackTrace();
					resultFlag = "F";
				}
				return resultFlag;
	}
	
	private boolean doesMailComesUnderRediffGroup(String to, String cc, String bcc) {
		boolean[] flag = new boolean[] { false };
		Arrays.stream(to.split(",")).forEach(email -> {
			String[] emailSplitted = email.split("@");
			if (emailSplitted.length == 2) {
				flag[0] = flag[0] || emailSplitted[1].toLowerCase().equals("rediffmail.com");
			}
		});
		Arrays.stream(cc.split(",")).forEach(email -> {
			String[] emailSplitted = email.split("@");
			if (emailSplitted.length == 2) {
				flag[0] = flag[0] || emailSplitted[1].toLowerCase().equals("rediffmail.com");
			}
		});
		Arrays.stream(bcc.split(",")).forEach(email -> {
			String[] emailSplitted = email.split("@");
			if (emailSplitted.length == 2) {
				flag[0] = flag[0] || emailSplitted[1].toLowerCase().equals("rediffmail.com");
			}
		});
		return flag[0];
	}
	
	public boolean sendEmails(String emailSubject, String templatePath, String uploadFilePath,Map<String,Object> model,String to,String cc,String bcc){
		try {
		SendViaEmailModel mailobj = new SendViaEmailModel();
		mailobj.setToMail(to);
		mailobj.setCcEmail(cc);
		mailobj.setBccEmail(bcc);
		mailobj.setSubject(emailSubject);
		model.put("sSysDate",new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		model.put("sSysTime",new SimpleDateFormat("HH:mm:ss").format(new Date()));
		/*
		Properties prop = new  Properties();
		prop.load(new FileInputStream(new File(getClass().getClassLoader().getResource("templates/foscos_configuration.properties").getFile())));
		*/
		model.put("HelpDeskName", "Developer Team");
		model.put("HelpDeskEmailID", "helpdesk@gmail.com");
		model.put("HelpDeskMobileNo", "1800112100");
		model.put("CopyRightName", "Developer");
		model.put("CopyRightYear", "2018");
		mailobj.setMailContent(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, model));
		sendEmails(mailobj);
		return true;
		} catch (Exception e) {
		e.printStackTrace();
		return false;
		}
		}

	
}

