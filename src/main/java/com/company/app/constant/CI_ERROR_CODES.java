package com.company.app.constant;

import com.company.app.dto.Status;
/**
 * Enum to maintain error codes and eagerly create 
 * status object for such error scenarios. Thus minimizing 
 * error object creation at run time. getStatus() works
 * as a Factory-Method and provides singleton instance
 * of status object for respective error codes. 
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
public enum CI_ERROR_CODES {

	NULL("99", "Company Info object in request cannbot be Null."),
	COMP_NAME("98"," A valid Company's Name must be provided; cannot be longer than 50"),
	COMP_NAME_ID("97", "A valid Company Name or Company ID is required to make an Update"),
	ADDRESS_NULL("96", "Address object must be provided with the request"),
	ADDRESS_FEILDS("95","Adress feilds: Address, City, Country are required an cannot be more than 50 chars"),
	NO_CI_RECORD("94", "No record found for that Company Name/Id."),
	NO_CI_RECORDS_FOUND("93", "No companies found"),
	PAGINATION_SIZE("92", "Page size and Page Number must be greater than 1");


	private String errValue;
	private String errCode;
	private Status errStatus;

	CI_ERROR_CODES(String errorCode, String errorValue){
		this.errValue = errorValue;
		this.errCode = errorCode;
		this.errStatus = new Status(false,false, errCode, errValue);
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrValue() {
		return errValue;
	}

	public Status getStatus(){
		return errStatus;
	}

}
