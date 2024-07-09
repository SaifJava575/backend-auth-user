package com.auth.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
@NamedQuery(name = "UserRoles.findAll", query = "SELECT aa FROM UserRoles aa")
public class UserRoles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    private UserAndUserRolesPk id;
	
	@Column(name = "active_flag")
	private Boolean activeFlag;

	@Column(name = "created_on", updatable = false)
	private Timestamp createdOn = new Timestamp(System.currentTimeMillis());

	@Column(name = "updated_on")
	private Timestamp updatedOn = new Timestamp(System.currentTimeMillis());
	
	@Column(name="created_by")
	private BigInteger createdBy;
	
	@Column(name="updated_by")
	private BigInteger updatedBy;

	public UserAndUserRolesPk getId() {
		return id;
	}

	public void setId(UserAndUserRolesPk id) {
		this.id = id;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
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
