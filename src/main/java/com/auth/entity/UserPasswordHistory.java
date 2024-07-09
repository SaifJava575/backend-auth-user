package com.auth.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_password_history", schema = "users")
@NamedQuery(name = "UserPasswordHistory.findAll", query = "SELECT uph FROM UserPasswordHistory uph")
public class UserPasswordHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pwd_hist_id")
	private Integer pwdHistId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "old_password")
	private String oldPassword;
	
	@Column(name = "old_password_updated_on")
	private Timestamp oldPasswordUpdated_on = new Timestamp(System.currentTimeMillis());

	@Column(name = "created_on", updatable = false)
	private Timestamp createdOn = new Timestamp(System.currentTimeMillis());

	@Column(name = "updated_on")
	private Timestamp updatedOn = new Timestamp(System.currentTimeMillis());

	@Column(name = "created_by")
	private BigInteger createdBy;

	@Column(name = "updated_by")
	private BigInteger updatedBy;

	public Integer getPwdHistId() {
		return pwdHistId;
	}

	public void setPwdHistId(Integer pwdHistId) {
		this.pwdHistId = pwdHistId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Timestamp getOldPasswordUpdated_on() {
		return oldPasswordUpdated_on;
	}

	public void setOldPasswordUpdated_on(Timestamp oldPasswordUpdated_on) {
		this.oldPasswordUpdated_on = oldPasswordUpdated_on;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigInteger getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}

	public BigInteger getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigInteger updatedBy) {
		this.updatedBy = updatedBy;
	}
}
