package com.company.app.dto;
/**
 * This is a DTO class which standardizes a 
 * Success and Error response scenarios. In a Status only API
 * calls and in error scenarios of the information-requesting
 * API calls this Status object is returned from the app.
 * 
 * @author Ben Saini
 *
 */
public class Status {
	
	private final boolean success;
	/*
	 *  boolean apiError flag used to indicate to the UI whether an
	 *  error is a global API error, or a item/page 
	 *  specific data entry error.
	 */
	private final boolean apiError;	
	private String messageCode="";
	private String message="";

	/*
	 * Several constructors helps provide Status 
	 * objects for different error scenarios by pre-setting 
	 * boolean flags.
	 */
	public Status() {
		this.success = false;	
		this.apiError = false;
		this.message = "";
	}

	public Status(boolean success) {
		this.success = success;
		this.apiError = false;
	}

	public Status(boolean success, String message) {
		this.success = success;
		this.apiError = false;
		this.message = (message == null ? "" : message);
	}

	public Status(boolean success, boolean apiError, String message) {
		this.success = success;
		this.apiError = apiError;
		this.message = (message == null ? "" : message);
	}

	public Status(boolean success, boolean apiError, Status oldStatus) {
		this.success = success;
		this.apiError = apiError;
		this.messageCode = oldStatus.getMessageCode();
		this.message = oldStatus.getMessage();
	}

	public Status(boolean success, boolean apiError, String messageCode, String message) {
		this.success = success;
		this.apiError = apiError;
		this.message = (message == null ? "" : message);
		this.messageCode = (messageCode == null ? "" : messageCode);
	}

	public final boolean isSuccess() {
		return success;
	}

	public final boolean isApiError() {
		return apiError;
	}

	public final String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
}
