package com.company.app.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.app.dto.Status;
/**
 * Any exception in this app is caught
 * by this class and filtered for custom or
 * generic Exception type and then handled acordingly.
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@ControllerAdvice
public class CompanyExceptionHandler {

	/*
	 * If custom exception, extract the status object
	 * and return.
	 */
	@ExceptionHandler(CompanyAppException.class)
	public @ResponseBody Status companyExceptionsHandler(CompanyAppException companyException){
		Status response = null;

		if(companyException != null){
			response = companyException.getStatus();
		}

		return response;
	}

	/*
	 * If not a custom exception, populate a status object
	 * and return.
	 */
	@ExceptionHandler(value = Exception.class)
	public @ResponseBody Status genericExceptionHandler(HttpServletRequest request, Exception ex){
		Status response = null;

		String errCode = ex.getClass().toString();
		String errMessage = ex.getMessage();

		response = new Status(false, false, errCode, errMessage);

		return response;
	}
}
