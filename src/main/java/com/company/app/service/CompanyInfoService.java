package com.company.app.service;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.app.config.CompanyAppException;
import com.company.app.constant.CI_ERROR_CODES;
import com.company.app.domain.BeneficialOwner;
import com.company.app.domain.CompanyInfo;
import com.company.app.dto.Status;
/**
 * It contains custom business logic and advanced Hibernate queries and
 * criteria that has been used to develop functionality creating and updating
 * company info and related business object in the database. This class allows
 * us to keep the generated DAO classes as-it-is and keep the custom @Repository 
 * logic separate.
 * 
 * 
 * @Author Ben Saini
 * 
 **Zero-Logs*
 * This app uses AOP logging; @see com.company.app.config.LoggingAspect.java
 */
@Transactional
@Service
public class CompanyInfoService {

	@Autowired
	private ValidationService validationService_;

	@Autowired
	private CompanyInfoDaoImpl companyInfoDao_;

	@Autowired
	private SessionFactory sessionFactory_;

	/*
	 * Leverages ValidationService to validate company 
	 * info object and then persists
	 */
	public Status addCompany(CompanyInfo companyInfo_){

		Status response = null;
		response = validationService_.validateAddCompany(companyInfo_);

		if(!response.isSuccess()){
			return response;
		}

		companyInfoDao_.persist(companyInfo_);

		response.setMessage("New company added. Id: "+ companyInfo_.getCompanyId());
		return response;
	}
	
	/*
	 * Leverages ValidationService to validate company 
	 * info. Looks up the record by using Company's Unique Name or 
	 * Unique Id. If a record is found we update(merge) the parameters 
	 * and commit in the database.
	 */
	@Transactional
	public CompanyInfo updateCompany(CompanyInfo companyInfo_) throws CompanyAppException{

		CompanyInfo response, companyRecordFound = null;
		Status status = null;
		Set<BeneficialOwner> beneficialOwners = null;
		List<CompanyInfo> companyRecordFoundList = null;

		status = validationService_.validateUpdateCompany(companyInfo_);

		if(!status.isSuccess()){
			throw new CompanyAppException(status);
		}

		companyRecordFoundList = findCompanyByIdOrName(companyInfo_.getCompanyId(), companyInfo_.getName());

		if(companyRecordFoundList!=null && !companyRecordFoundList.isEmpty()){
			companyRecordFound = companyRecordFoundList.get(0);
			companyInfo_.setCompanyId(companyRecordFound.getCompanyId());

			//Prevent null values being updated to Email & Phone
			if(companyInfo_.getEmail() == null){
				companyInfo_.setEmail(companyRecordFound.getEmail());
			}
			if(companyInfo_.getPhone() == null){
				companyInfo_.setPhone(companyRecordFound.getPhone());
			}

			//To handle Addition of New BeneficialOwners
			//without Overwriting existing ones
			beneficialOwners = companyInfo_.getBeneficialOwners();
			if(beneficialOwners != null && !beneficialOwners.isEmpty()){
				beneficialOwners.addAll(companyRecordFound.getBeneficialOwners());
			}

			response = companyInfoDao_.merge(companyInfo_);
		}else{
			status = CI_ERROR_CODES.NO_CI_RECORD.getStatus();
			throw new CompanyAppException(status);
		}

		return response;
	}
	
	
	/*
	 * Lazy loads a list of companies using the pageNum and 
	 * pageSize to balance the load on APIs and only serve a
	 * set of data at a time. Pagination-wise client.
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CompanyInfo> getCompanyList(int pageNum, int pageSize)throws CompanyAppException{
		List<CompanyInfo> response = null;

		if(pageNum <1 || pageSize < 1){
			throw new CompanyAppException(CI_ERROR_CODES.PAGINATION_SIZE.getStatus());
		}

		Session session = sessionFactory_.getCurrentSession();
		Criteria criteria = session.createCriteria(CompanyInfo.class);
		criteria.setFirstResult((pageNum-1)* pageSize);
		criteria.setMaxResults(pageSize);

		response = (List<CompanyInfo>) criteria.list();

		if(response == null || response.isEmpty()){
			throw new CompanyAppException(CI_ERROR_CODES.NO_CI_RECORDS_FOUND.getStatus());
		}

		return response;	
	}
	
	
	/*
	 * Leverages ValidationService to validate company 
	 * info. Looks up the record by using Company's Unique Name or 
	 * Unique Id. Eager loads and returns all the company info.
	 * 
	 */
	public CompanyInfo getCompanyDetail(CompanyInfo companyInfo_) throws CompanyAppException{
		CompanyInfo response = null;
		Status status = null;
		int companyId;
		String companyName;

		status = validationService_.validateDetailCompany(companyInfo_);	

		if(!status.isSuccess()){
			throw new CompanyAppException(status);
		}

		companyId = companyInfo_.getCompanyId();
		companyName = companyInfo_.getName();

		List<CompanyInfo> companyList = findCompanyByIdOrName(companyId, companyName);

		if(companyList == null || companyList.isEmpty()){
			throw new CompanyAppException(CI_ERROR_CODES.NO_CI_RECORDS_FOUND.getStatus());
		}

		response = companyList.get(0);

		return response;
	}
	
	
	/*
	 * Common private method allows to do a company info
	 * look up by name or id
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	private List<CompanyInfo> findCompanyByIdOrName(int companyId, String companyName){
		List<CompanyInfo> response = null;

		Session session = sessionFactory_.getCurrentSession();
		Criteria criteria = session.createCriteria(CompanyInfo.class, "companyInfo");
		criteria.setFetchMode("address", FetchMode.JOIN);
		criteria.setFetchMode("beneficialOwners", FetchMode.JOIN);

		SimpleExpression restrictionCompanyId = Restrictions.eq("companyId", companyId);
		SimpleExpression restrictionCompanyName = Restrictions.eq("name", companyName);

		if(companyId > 0){
			criteria.add(restrictionCompanyId);
		}else{
			criteria.add(restrictionCompanyName);
		}

		response = (List<CompanyInfo>) criteria.list();

		return response;
	}

}
