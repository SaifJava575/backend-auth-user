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
@Table(name = "master_user_roles")
@NamedQuery(name = "MasterUserRoles.findAll", query = "SELECT aa FROM MasterUserRoles aa")
public class MasterUserRoles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "role_name_desc")
	private String roleNameDesc;

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleNameDesc() {
		return roleNameDesc;
	}

	public void setRoleNameDesc(String roleNameDesc) {
		this.roleNameDesc = roleNameDesc;
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
