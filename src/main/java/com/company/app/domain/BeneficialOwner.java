package com.company.app.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * This class maps the beneficial_owner table into Java. 
 * This class was auto-generated by Hibernate tools.
 * @Id@GenratedValue and updatable propertiese were
 * updated.
 * 
 * Sample BeneficialOwner JSON:
 *  { "firstName":"Ben",
 *         "lastName":"Saini"
 *   } 
 * 
 */
@Entity
@Table(name = "beneficial_owner", schema = "public")
public class BeneficialOwner implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int ownerId;
	private String firstName;
	private String lastName;
	private Set<CompanyInfo> companyInfos = new HashSet<CompanyInfo>(0);

	public BeneficialOwner() {
	}

	public BeneficialOwner(int ownerId, String firstName) {
		this.ownerId = ownerId;
		this.firstName = firstName;
	}

	public BeneficialOwner(int ownerId, String firstName, String lastName,
			Set<CompanyInfo> companyInfos) {
		this.ownerId = ownerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyInfos = companyInfos;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "owner_id", unique = true, nullable = false)
	public int getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "first_name", nullable = false, length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "beneficialOwners")
	public Set<CompanyInfo> getCompanyInfos() {
		return this.companyInfos;
	}

	public void setCompanyInfos(Set<CompanyInfo> companyInfos) {
		this.companyInfos = companyInfos;
	}

}