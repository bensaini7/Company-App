package com.company.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.app.config.CompanyAppException;
import com.company.app.domain.CompanyInfo;
import com.company.app.dto.Status;
import com.company.app.service.CompanyInfoService;
/**
 * It accepts all requests pertaining managing Company and
 * related data sets. All Company related APIs for the 
 * application are in this controller.
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@RestController
public class CompanyController {

	@Autowired
	private CompanyInfoService companyInfoService_;
	
	/*
	 * Create Company takes a JSON form of the Company Object as request.
	 * Upon validation for required parameters it persists in the DB.
	 * 
	 * Please see CompanyInfo.java for a sample request JSON object.
	 */
	@RequestMapping(value = "/createCompany/", method = RequestMethod.POST, consumes="application/json")
	public  Status createNewCompany(@RequestBody CompanyInfo companyInfo_){
		Status response = null;

		response = companyInfoService_.addCompany(companyInfo_);

		return response;
	}
	
	/*
	 * Update Company takes a JSON form of the Company Object as request.
	 * Upon validation for required parameters it Updates info in DB.
	 * 
	 * It is used to achieve the following use cases:
	 * 1. Add owners
	 * 2. Update Address fields
	 * 3. Update Company fields
	 * 
	 * Please see CompanyInfo.java for a sample request JSON object.
	 */
	@RequestMapping(value = "/updateCompany/", method = RequestMethod.POST, consumes="application/json")
	public CompanyInfo updateCompany(@RequestBody CompanyInfo companyInfo_) throws CompanyAppException{
		CompanyInfo response = null;
		
		response = companyInfoService_.updateCompany(companyInfo_);

		return response;
	}
	
	/*
	 * Follows pagination practices to fetch a list of companies 
	 * from the Database. It does not eagerly fetches Beneficial
	 * Owners info, thus only returns Company Info and Address,
	 * mapped by @Many-to-One relation, for each company in 
	 * the returned list.
	 * 
	 * @Input pageNum(int) and pageSize(int)
	 * @Output  List of Companies found. If no companies exist
	 * 			returns an error in Status object.
	 * 
	 * Please see CompanyInfo.java for a sample response JSON object.
	 */

	@RequestMapping(value = {"/getCompanyList/","/getCompanyList"}, method = {RequestMethod.POST,RequestMethod.GET})
	public List<CompanyInfo> getCompanyList(@RequestParam int pageNum, @RequestParam int pageSize) throws CompanyAppException{
		List<CompanyInfo> response = null;
		
		response = companyInfoService_.getCompanyList(pageNum, pageSize);

		return response;
	}
	
	/*
	 * Gets Company details including a Set of all the beneficial
	 * owners mapped to a company via @Many-to-Many relationship
	 * 
	 * Please see CompanyInfo.java for a sample request JSON object.
	 */
	
	@RequestMapping(value = "/getCompanyDetails/", method = RequestMethod.POST, consumes="application/json")
	public CompanyInfo getCompanyDetails(@RequestBody CompanyInfo companyInfo_) throws CompanyAppException{
		CompanyInfo response = null;
	
		response = companyInfoService_.getCompanyDetail(companyInfo_);

		return response;
	}



}
