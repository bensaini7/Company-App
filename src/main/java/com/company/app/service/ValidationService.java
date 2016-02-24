package com.company.app.service;

import org.springframework.stereotype.Service;

import com.company.app.constant.CI_ERROR_CODES;
import com.company.app.domain.Address;
import com.company.app.domain.CompanyInfo;
import com.company.app.dto.Status;
import com.google.common.base.Strings;

/**
 * This service is responsible to hold all business logic to pro-actively
 * validate incoming request parameters before request can be forwarded to
 * other services for @Repository related functionality.
 *	 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@Service
public class ValidationService {

	private static final int COLUMN_SIZE_RESTRICTON = 50;


	public Status validateAddCompany(CompanyInfo companyInfo_){
		Status response = null;
		Address addressLocal = null;

		if(companyInfo_ !=null){

			if(!isLessThanStrLength(companyInfo_.getName(), COLUMN_SIZE_RESTRICTON)){
				return CI_ERROR_CODES.COMP_NAME.getStatus();
			}
			addressLocal = companyInfo_.getAddress();
			if(addressLocal == null){
				return CI_ERROR_CODES.ADDRESS_NULL.getStatus();
			}
			if(!isLessThanStrLength(addressLocal.getAddress(), COLUMN_SIZE_RESTRICTON) || !isLessThanStrLength(addressLocal.getCity(), COLUMN_SIZE_RESTRICTON)
					|| !isLessThanStrLength(addressLocal.getCountry(), COLUMN_SIZE_RESTRICTON)){
				return CI_ERROR_CODES.ADDRESS_FEILDS.getStatus();
			}

			response = new Status(true, false, "", "");

		}else{
			response =  CI_ERROR_CODES.NULL.getStatus();
		}

		return response;		
	}

	public Status validateDetailCompany(CompanyInfo companyInfo_){
		return validateUpdateCompany(companyInfo_);
	}

	public Status validateUpdateCompany(CompanyInfo companyInfo_){
		Status response = null;

		if(companyInfo_ !=null){

			if(!isLessThanStrLength(companyInfo_.getName(), COLUMN_SIZE_RESTRICTON) && companyInfo_.getCompanyId() == 0){
				return CI_ERROR_CODES.COMP_NAME_ID.getStatus();
			}
			response = new Status(true, false, "", "");
		}else{
			response =  CI_ERROR_CODES.NULL.getStatus();
		}

		return response;		
	}
	/*
	 * Validates Non-Null and Max Length Restrictions
	 * to comply with DB Schema
	 */
	private boolean isLessThanStrLength(String input, int maxLenght){
		boolean response = false;

		if(!Strings.isNullOrEmpty(input)){
			if(input.length() < maxLenght){
				response = true;
			}
		}

		return response;
	}
}
