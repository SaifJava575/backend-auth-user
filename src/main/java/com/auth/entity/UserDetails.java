package com.auth.entity;

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
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

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

   
}
