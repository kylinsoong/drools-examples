package org.drools.examples.model;

import java.io.Serializable;
import java.util.List;

import org.kie.api.definition.type.Label;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1999859475742335503L;
	
	@Label("Age")
	private Integer age;
	   
	@Label("ApplicableXpressCreditProductsList")
	private List<XPressProduct> applicableXpressCreditProductsList;
	   
	@Label("Nature of Employment")
	private String natureOfEmployment;
	
	@Label("Employer Organization Type")
	private String employerOrgType;
	
	@Label("Length of Service in Organization")
	private Double lenthOfServiceinYears;
	
	@Label("length Of Service Remaining In Years")
	private Integer lengthOfServiceRemainingInYears;
	
	@Label("Designation")
	private String designation;
	
	@Label("Approver Of Employer")
	private String approverOfEmployer;
	
	@Label("Name Of Employer to be Validate")
	private Boolean nameOfEmployerToValidate;
	
	@Label("Facility Type Of Loan")
	private String facilityTypeOfLOan;
	
	@Label("HouseOwnership")
	private String houseOwnership;
	
	@Label("Education Qualification")
	private String educationQualification;
	
	@Label("OccupationType")
	private String occupationType;
	
	@Label("FinancialInfo")
	private FinancialInfo financialInfo;
	
	@Label("defenseForceType")
	private String defenseForceType;
	
	@Label("BusinessExceptionList")
	private List<BuisnessExceptions> businessExceptionList;
	
	@Label("retirementAge")
	private Integer retirementAge;
	
	@Label("contractPeriod")
	private Integer contractPeriod;
	
	@Label("typeOfLoanFacility")
	private String typeOfLoanFacility;
	
	@Label("Organization type")
	private String organizationType;
	
	public Customer() {
		
	}

	public Customer(Integer age, List<XPressProduct> applicableXpressCreditProductsList, String natureOfEmployment,
			String employerOrgType, Double lenthOfServiceinYears, Integer lengthOfServiceRemainingInYears,
			String designation, String approverOfEmployer, Boolean nameOfEmployerToValidate, String facilityTypeOfLOan,
			String houseOwnership, String educationQualification, String occupationType, FinancialInfo financialInfo,
			String defenseForceType, List<BuisnessExceptions> businessExceptionList, Integer retirementAge,
			Integer contractPeriod, String typeOfLoanFacility, String organizationType) {
		super();
		this.age = age;
		this.applicableXpressCreditProductsList = applicableXpressCreditProductsList;
		this.natureOfEmployment = natureOfEmployment;
		this.employerOrgType = employerOrgType;
		this.lenthOfServiceinYears = lenthOfServiceinYears;
		this.lengthOfServiceRemainingInYears = lengthOfServiceRemainingInYears;
		this.designation = designation;
		this.approverOfEmployer = approverOfEmployer;
		this.nameOfEmployerToValidate = nameOfEmployerToValidate;
		this.facilityTypeOfLOan = facilityTypeOfLOan;
		this.houseOwnership = houseOwnership;
		this.educationQualification = educationQualification;
		this.occupationType = occupationType;
		this.financialInfo = financialInfo;
		this.defenseForceType = defenseForceType;
		this.businessExceptionList = businessExceptionList;
		this.retirementAge = retirementAge;
		this.contractPeriod = contractPeriod;
		this.typeOfLoanFacility = typeOfLoanFacility;
		this.organizationType = organizationType;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<XPressProduct> getApplicableXpressCreditProductsList() {
		return applicableXpressCreditProductsList;
	}

	public void setApplicableXpressCreditProductsList(List<XPressProduct> applicableXpressCreditProductsList) {
		this.applicableXpressCreditProductsList = applicableXpressCreditProductsList;
	}

	public String getNatureOfEmployment() {
		return natureOfEmployment;
	}

	public void setNatureOfEmployment(String natureOfEmployment) {
		this.natureOfEmployment = natureOfEmployment;
	}

	public String getEmployerOrgType() {
		return employerOrgType;
	}

	public void setEmployerOrgType(String employerOrgType) {
		this.employerOrgType = employerOrgType;
	}

	public Double getLenthOfServiceinYears() {
		return lenthOfServiceinYears;
	}

	public void setLenthOfServiceinYears(Double lenthOfServiceinYears) {
		this.lenthOfServiceinYears = lenthOfServiceinYears;
	}

	public Integer getLengthOfServiceRemainingInYears() {
		return lengthOfServiceRemainingInYears;
	}

	public void setLengthOfServiceRemainingInYears(Integer lengthOfServiceRemainingInYears) {
		this.lengthOfServiceRemainingInYears = lengthOfServiceRemainingInYears;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getApproverOfEmployer() {
		return approverOfEmployer;
	}

	public void setApproverOfEmployer(String approverOfEmployer) {
		this.approverOfEmployer = approverOfEmployer;
	}

	public Boolean getNameOfEmployerToValidate() {
		return nameOfEmployerToValidate;
	}

	public void setNameOfEmployerToValidate(Boolean nameOfEmployerToValidate) {
		this.nameOfEmployerToValidate = nameOfEmployerToValidate;
	}

	public String getFacilityTypeOfLOan() {
		return facilityTypeOfLOan;
	}

	public void setFacilityTypeOfLOan(String facilityTypeOfLOan) {
		this.facilityTypeOfLOan = facilityTypeOfLOan;
	}

	public String getHouseOwnership() {
		return houseOwnership;
	}

	public void setHouseOwnership(String houseOwnership) {
		this.houseOwnership = houseOwnership;
	}

	public String getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(String educationQualification) {
		this.educationQualification = educationQualification;
	}

	public String getOccupationType() {
		return occupationType;
	}

	public void setOccupationType(String occupationType) {
		this.occupationType = occupationType;
	}

	public FinancialInfo getFinancialInfo() {
		return financialInfo;
	}

	public void setFinancialInfo(FinancialInfo financialInfo) {
		this.financialInfo = financialInfo;
	}

	public String getDefenseForceType() {
		return defenseForceType;
	}

	public void setDefenseForceType(String defenseForceType) {
		this.defenseForceType = defenseForceType;
	}

	public List<BuisnessExceptions> getBusinessExceptionList() {
		return businessExceptionList;
	}

	public void setBusinessExceptionList(List<BuisnessExceptions> businessExceptionList) {
		this.businessExceptionList = businessExceptionList;
	}

	public Integer getRetirementAge() {
		return retirementAge;
	}

	public void setRetirementAge(Integer retirementAge) {
		this.retirementAge = retirementAge;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getTypeOfLoanFacility() {
		return typeOfLoanFacility;
	}

	public void setTypeOfLoanFacility(String typeOfLoanFacility) {
		this.typeOfLoanFacility = typeOfLoanFacility;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

}
