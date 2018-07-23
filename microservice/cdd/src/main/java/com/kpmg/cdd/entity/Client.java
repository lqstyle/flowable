package com.kpmg.cdd.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Function Description: CLient entity info
 *
 * @param:
 * @return:
 * @author: lucasliang
 * @date: 07/07/2018
 */

public class Client implements Serializable {

	private static final long serialVersionUID = -8410653501201712921L;

	private String id;

	private String caseId;

	private String name;

	private String industry;

	private String phone;

	private String email;

	private String faxNo;

	private String country;

	private String province;

	private String address;

	private String entityType;

	private String resultState;

	private List<String> requiredDocumentList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getResultState() {
		return resultState;
	}

	public void setResultState(String resultState) {
		this.resultState = resultState;
	}

	public List<String> getRequiredDocumentList() {
		return requiredDocumentList;
	}

	public void addDocument(String document) {
		if (this.requiredDocumentList==null) {
			this.requiredDocumentList = new ArrayList<>();
		}
		this.requiredDocumentList.add(document);
	}
}