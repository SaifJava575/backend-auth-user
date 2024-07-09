package com.auth.bean;

public class Status {

	private String StatusCode;
	private String GeneratedCode;
	private String UserId;

	public Status() {
	}

	public Status(String StatusCode, String GeneratedCode) {
		this.StatusCode = StatusCode;
		this.GeneratedCode = GeneratedCode;
	}
	public Status(String StatusCode, String GeneratedCode,String UserId) {
		this.StatusCode = StatusCode;
		this.GeneratedCode = GeneratedCode;
		this.UserId=UserId;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(String StatusCode) {
		this.StatusCode = StatusCode;
	}

	public String getGeneratedCode() {
		return GeneratedCode;
	}

	public void setGeneratedCode(String GeneratedCode) {
		this.GeneratedCode = GeneratedCode;
	}
}
