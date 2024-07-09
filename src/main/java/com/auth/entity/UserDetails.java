package com.auth.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_details")
public class UserDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "login_id", nullable = false, unique = true)
	private String loginId;

	@Column(name = "user_pwd", nullable = false)
	private String userPwd;

	@Column(name = "user_mobile_no")
	private String userMobileNo;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "personal_email")
	private String personalEmail;

	@Column(name = "personal_mobile_no")
	private String personalMobileNo;

	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;

	@Column(name = "designation")
	private String designation;

	@Column(name = "active_flag", nullable = false)
	private Boolean activeFlag;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "temp_otp")
	private String tempOtp;

	@Column(name = "dev_pwd")
	private String devPwd;

	@Column(name = "last_change_password_updated_on")
	private LocalDateTime lastChangePasswordUpdatedOn;

	@Column(name = "last_login_updated_on")
	private LocalDateTime lastLoginUpdatedOn;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "address")
	private String address;

	@Column(name = "country")
	private String country;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "website")
	private String website;

	@Column(name = "is_login_notification_enabled", nullable = false)
	private Boolean isLoginNotificationEnabled;

	@Column(name = "is_sms_notification_enabled", nullable = false)
	private Boolean isSmsNotificationEnabled;

	@Column(name = "secret_question_id")
	private Integer secretQuestionId;

	@Column(name = "secret_question_answer")
	private String secretQuestionAnswer;

	@Column(name = "others_name")
	private String othersName;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getPersonalMobileNo() {
		return personalMobileNo;
	}

	public void setPersonalMobileNo(String personalMobileNo) {
		this.personalMobileNo = personalMobileNo;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTempOtp() {
		return tempOtp;
	}

	public void setTempOtp(String tempOtp) {
		this.tempOtp = tempOtp;
	}

	public String getDevPwd() {
		return devPwd;
	}

	public void setDevPwd(String devPwd) {
		this.devPwd = devPwd;
	}

	public LocalDateTime getLastChangePasswordUpdatedOn() {
		return lastChangePasswordUpdatedOn;
	}

	public void setLastChangePasswordUpdatedOn(LocalDateTime lastChangePasswordUpdatedOn) {
		this.lastChangePasswordUpdatedOn = lastChangePasswordUpdatedOn;
	}

	public LocalDateTime getLastLoginUpdatedOn() {
		return lastLoginUpdatedOn;
	}

	public void setLastLoginUpdatedOn(LocalDateTime lastLoginUpdatedOn) {
		this.lastLoginUpdatedOn = lastLoginUpdatedOn;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Boolean getIsLoginNotificationEnabled() {
		return isLoginNotificationEnabled;
	}

	public void setIsLoginNotificationEnabled(Boolean isLoginNotificationEnabled) {
		this.isLoginNotificationEnabled = isLoginNotificationEnabled;
	}

	public Boolean getIsSmsNotificationEnabled() {
		return isSmsNotificationEnabled;
	}

	public void setIsSmsNotificationEnabled(Boolean isSmsNotificationEnabled) {
		this.isSmsNotificationEnabled = isSmsNotificationEnabled;
	}

	public Integer getSecretQuestionId() {
		return secretQuestionId;
	}

	public void setSecretQuestionId(Integer secretQuestionId) {
		this.secretQuestionId = secretQuestionId;
	}

	public String getSecretQuestionAnswer() {
		return secretQuestionAnswer;
	}

	public void setSecretQuestionAnswer(String secretQuestionAnswer) {
		this.secretQuestionAnswer = secretQuestionAnswer;
	}

	public String getOthersName() {
		return othersName;
	}

	public void setOthersName(String othersName) {
		this.othersName = othersName;
	}

}
