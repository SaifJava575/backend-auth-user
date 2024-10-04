package com.auth.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.auth.bean.JavaConstant;
import com.auth.bean.SendViaEmailModel;
import com.auth.dao.IGenericDao;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UtilProperty {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilProperty.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private IGenericDao iGenericDao;
	
	 @Value("${auth.doc.path}")
	 private String fileUploadPath;

	public void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
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
					Objects.toString(transMailQueueSender.getCcEmail(), ""),
					Objects.toString(transMailQueueSender.getBccEmail(), ""))) {
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED);
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
				resultFlag = "Y";
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

	public boolean sendEmails(String emailSubject, String templatePath, String uploadFilePath,
			Map<String, Object> model, String to, String cc, String bcc) {
		try {
			SendViaEmailModel mailobj = new SendViaEmailModel();
			mailobj.setToMail(to);
			mailobj.setCcEmail(cc);
			mailobj.setBccEmail(bcc);
			mailobj.setSubject(emailSubject);
			model.put("sSysDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
			model.put("sSysTime", new SimpleDateFormat("HH:mm:ss").format(new Date()));
			/*
			 * Properties prop = new Properties(); prop.load(new FileInputStream(new
			 * File(getClass().getClassLoader().getResource(
			 * "templates/foscos_configuration.properties").getFile())));
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
	
	@SuppressWarnings("unlikely-arg-type")
	public String UploadFile( MultipartFile inputFile) {
		logger.info("UploadFile called ");
		String filePath = null;
		if (inputFile != null && !inputFile.equals("")) {
			if (!inputFile.isEmpty()) {
				try {
					//					prop.load(new FileInputStream(
//							new File(getClass().getClassLoader().getResource("application.properties").getFile())));
					String thisYear = new SimpleDateFormat("yyyy").format(new Date());
					String thisMonth = new SimpleDateFormat("MM").format(new Date());
					String thisDate = new SimpleDateFormat("dd").format(new Date());
					String originalFilename = inputFile.getOriginalFilename();
					//filePath = thisYear + "/" + thisMonth + "/" + thisDate + "/"
					//		 + UUID.randomUUID() + "."
					filePath = "/saif/"
							 + UUID.randomUUID() + "."
							+ FilenameUtils.getExtension(originalFilename);
					File destinationFile = new File(fileUploadPath + filePath);
					if (!destinationFile.exists()) {
						if (destinationFile.mkdirs()) {
							inputFile.transferTo(destinationFile);
						}
					} else {
						inputFile.transferTo(destinationFile);
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
		}
		return filePath;

	}
	
	@SuppressWarnings("unchecked")
	public void viewRawPdf(Integer documentId, HttpServletResponse response) {
		try {
			String documentPath ="";
			if(documentId!=null ) {
				List<?> filePath =  iGenericDao.executeDDLHQL(JavaConstant.GET_DOCUMENT_PATH_FILE,
						new Object[] { documentId });
				Map<String,Object> map=(Map<String, Object>) filePath.get(0);
				documentPath=(String) map.get("filePath");
			}
			 
			String filePath = fileUploadPath + documentPath;
			//String filePath="D:/rawdata/uploaded/2023/12/raw0cd6753b-2681-4a81-8ca7-00764554b479.pdf";
			logger.info("filePath " + filePath);
			Path file = Paths.get(filePath);
			if (Files.exists(file)) {
				response.addHeader("Content-Disposition", "attachment; filename=" + filePath);
				try {
					Files.copy(file, response.getOutputStream());
					System.out.println(file);
					response.getOutputStream().flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
