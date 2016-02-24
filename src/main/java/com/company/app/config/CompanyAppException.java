package com.company.app.config;

import com.company.app.dto.Status;
/**
 * Custom exception for the application.It is used 
 * to exit out the chain of execution when such an
 * error exception has occurred
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
public class CompanyAppException extends Exception{
	
	private static final long serialVersionUID = 1L;

	private Status status = null;

	public CompanyAppException(Status status){
		this.setStatus(status);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
