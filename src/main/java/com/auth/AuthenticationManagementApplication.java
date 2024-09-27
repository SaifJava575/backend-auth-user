package com.auth;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class AuthenticationManagementApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

    @Bean
    @Primary
    JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		// final String user="itlearn575@gmail.com";//change accordingly  
		  //final String password="ntmulytioeyxfano";//change accordingly  
		   
		mailSender.setUsername("itlearn575@gmail.com");
		mailSender.setPassword("ntmulytioeyxfano");

		System.out.println("================calling normal email=================");

		Properties props = mailSender.getJavaMailProperties();

    	 props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 props.put("mail.smtp.socketFactory.fallback", "true");
		 props.put("mail.transport.protocol", "smtp");
		 props.put("mail.smtp.host", "smtp.gmail.com");
		 props.put("mail.smtp.auth", "true");
		 props.put("mail.smtp.starttls.enable", "true");
		 props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		 props.put("mail.smtp.port", "465");
		 props.put("mail.smtp.debug", "true");
		
		return mailSender;
	}

    @Bean
    VelocityEngine getVelocityEngine() throws VelocityException, IOException{
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();      
    }

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(
				"=============================Starting TRAINING-SERVICE===========================================");
		System.out.println("CLIENT_PORT Value : " + environment.getProperty("CLIENT_PORT"));
		System.out.println("EUREKA_DEFAULT_ZONE Value : " + environment.getProperty("EUREKA_DEFAULT_ZONE"));
		System.out.println("DATASOURCE_URL Value : " + environment.getProperty("DATASOURCE_URL"));
		System.out.println("DATASOURCE_USERNAME Value : " + environment.getProperty("DATASOURCE_USERNAME"));
		System.out.println("DATASOURCE_PASSWORD Value : " + environment.getProperty("DATASOURCE_PASSWORD"));
		System.out.println(
				"DATASOURCE_HIKARI_AUTO_COMMIT Value : " + environment.getProperty("DATASOURCE_HIKARI_AUTO_COMMIT"));
		System.out.println("DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE Value : "
				+ environment.getProperty("DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE"));
		System.out.println("DATASOURCE_HIKARI_MINIMIUM_IDLE Value : "
				+ environment.getProperty("DATASOURCE_HIKARI_MINIMIUM_IDLE"));
		System.out.println(
				"DATASOURCE_HIKARI_IDLE_TIMEOUT Value : " + environment.getProperty("DATASOURCE_HIKARI_IDLE_TIMEOUT"));
		System.out
				.println("DATASOURCE_CONTINUEONERROR Value : " + environment.getProperty("DATASOURCE_CONTINUEONERROR"));
		System.out.println("JPA_GENERATE_DDL Value : " + environment.getProperty("JPA_GENERATE_DDL"));
		System.out.println("JPA_SHOW_SQL Value : " + environment.getProperty("JPA_SHOW_SQL"));
		System.out.println("JPA_HIBERNATE_DDL_AUTO Value : " + environment.getProperty("JPA_HIBERNATE_DDL_AUTO"));
		System.out.println("HIBERNATE_TEMP_USE_JDBC_METADATA_DEFAULTS Value : "
				+ environment.getProperty("HIBERNATE_TEMP_USE_JDBC_METADATA_DEFAULTS"));
		System.out.println("MULTIPART_ENABLED Value : " + environment.getProperty("MULTIPART_ENABLED"));
		System.out.println(
				"MULTIPART_FILE_SIZE_THRESHOLD Value : " + environment.getProperty("MULTIPART_FILE_SIZE_THRESHOLD"));
		System.out.println("MULTIPART_MAX_FILE_SIZE Value : " + environment.getProperty("MULTIPART_MAX_FILE_SIZE"));

	}

}
