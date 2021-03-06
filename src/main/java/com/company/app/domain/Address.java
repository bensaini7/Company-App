package com.company.app.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class maps the Address table into Java. 
 * This class was auto-generated by Hibernate tools.
 * @Id@GenratedValue and updatable propertiese were
 * updated.
 * 
 * Sample Address JSON:
 *     "address":{
 *         "address":"999 Vicious Circle",
 *         "city":"Seattle",
 *         "country":"USA"
 *     }
 */
@Entity
@Table(name = "address", schema = "public")
public class Address implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int addId;
	private String address;
	private String city;
	private String country;
	private Set<CompanyInfo> companyInfos = new HashSet<CompanyInfo>(0);

	public Address() {
	}

	public Address(int addId, String address) {
		this.addId = addId;
		this.address = address;
	}

	public Address(int addId, String address, String city, String country,
			Set<CompanyInfo> companyInfos) {
		this.addId = addId;
		this.address = address;
		this.city = city;
		this.country = country;
		this.companyInfos = companyInfos;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "add_id", unique = true, nullable = false)
	public int getAddId() {
		return this.addId;
	}

	public void setAddId(int addId) {
		this.addId = addId;
	}

	@Column(name = "address", nullable = false, length = 50)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", length = 50)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "country", length = 50)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "address")
	public Set<CompanyInfo> getCompanyInfos() {
		return this.companyInfos;
	}

	public void setCompanyInfos(Set<CompanyInfo> companyInfos) {
		this.companyInfos = companyInfos;
	}

}
