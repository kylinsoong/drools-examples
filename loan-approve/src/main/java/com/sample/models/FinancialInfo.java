package com.sample.models;

import java.io.Serializable;
import org.kie.api.definition.type.Label;


public class FinancialInfo implements Serializable {

    private static final long serialVersionUID = -6003677550129579678L;
    
    @Label("EMINMI Ratio")
    private Double EMINMIRatio;
  
    @Label("GMI")
    private Double GMI;
  
    @Label("NMI")
    private Double NMI;
  
    @Label("NMIGMIRatio")
    private Double NMIGMIRatio;
  
    @Label("cibilScore")
    private Integer cibilScore;
  
    @Label("creditGrade")
    private String creditGrade;
  
    @Label("RGScore")
    private Integer RGScore;
  
    @Label("riskScore")
    private Integer riskScore;
  
    @Label("existingTopupDisbursedMonths")
    private Integer existingTopupDisbursedMonths;
  
    @Label("isXBSalaryAccount")
    private String isXBSalaryAccount;
  
    @Label("modeOfRepayment")
    private String modeOfRepayment;
  
    @Label("loanTenure")
    private Integer loanTenure;
  
    @Label("noOfXpressCreditTopups")
    private Integer noOfXpressCreditTopups;
  
    @Label("xpressCreditTopupAllowed")
    private Boolean xpressCreditTopupAllowed;
  
    @Label("salaryAccountPeriod")
    private Integer salaryAccountPeriod;
  
    @Label("salaryAccountMandatory")
    private String salaryAccountMandatory;
  
    @Label("isDisbursementPeriodtoCheck")
    private Boolean isDisbursementPeriodtoCheck;
  
    @Label("facilityTypeOfLOan")
    private String facilityTypeOfLOan;
  
    @Label("Risk Scoring")
    private Double riskScoring;
  
    @Label("Cibil Account status")
    private String cibilAccountStatus;
  
    @Label("Number of Enquires within month")
    private Integer numberOfEnquiresWithinMonth;
  
    @Label("Number of Overdue of the last 24 months")
    private Integer numberOfOverdueLast24Months;
  
    @Label("CIRApproval")
    private String CIRApproval;
  
    @Label("DPDMoreThan30Days")
    private boolean DPDMoreThan30Days;
  
    @Label("NoOfDPDOver60DaysinYears")
    private Integer noOfDPDOver60DaysinYears;
  
    @Label("ElgibleLoanAmount")
    private Double elgibleLoanAmount;
  
    @Label("existingEMIAmount")
    private Double existingEMIAmount;
  
    @Label("BorrowerLoanSanction")
    private String borrowerLoanSanction;
  
    @Label("Credit Rating")
    private String creditRating;
  
    @Label("typeOfCheckOff")
    private String typeOfCheckOff;
  
    @Label("typeofMCLR")
    private String typeofMCLR;
  
    @Label("Spread")
    private Character spread;
  
    @Label("Interest Rate")
    private Double interestRate;
  
    public FinancialInfo() {}

    public FinancialInfo(Double eMINMIRatio, Double gMI, Double nMI, Double nMIGMIRatio, Integer cibilScore,
            String creditGrade, Integer rGScore, Integer riskScore, Integer existingTopupDisbursedMonths,
            String isXBSalaryAccount, String modeOfRepayment, Integer loanTenure, Integer noOfXpressCreditTopups,
            Boolean xpressCreditTopupAllowed, Integer salaryAccountPeriod, String salaryAccountMandatory,
            Boolean isDisbursementPeriodtoCheck, String facilityTypeOfLOan, Double riskScoring,
            String cibilAccountStatus, Integer numberOfEnquiresWithinMonth, Integer numberOfOverdueLast24Months,
            String cIRApproval, boolean dPDMoreThan30Days, Integer noOfDPDOver60DaysinYears, Double elgibleLoanAmount,
            Double existingEMIAmount, String borrowerLoanSanction, String creditRating, String typeOfCheckOff,
            String typeofMCLR, Character spread, Double interestRate) {
        super();
        this.EMINMIRatio = eMINMIRatio;
        this.GMI = gMI;
        this.NMI = nMI;
        this.NMIGMIRatio = nMIGMIRatio;
        this.cibilScore = cibilScore;
        this.creditGrade = creditGrade;
        this.RGScore = rGScore;
        this.riskScore = riskScore;
        this.existingTopupDisbursedMonths = existingTopupDisbursedMonths;
        this.isXBSalaryAccount = isXBSalaryAccount;
        this.modeOfRepayment = modeOfRepayment;
        this.loanTenure = loanTenure;
        this.noOfXpressCreditTopups = noOfXpressCreditTopups;
        this.xpressCreditTopupAllowed = xpressCreditTopupAllowed;
        this.salaryAccountPeriod = salaryAccountPeriod;
        this.salaryAccountMandatory = salaryAccountMandatory;
        this.isDisbursementPeriodtoCheck = isDisbursementPeriodtoCheck;
        this.facilityTypeOfLOan = facilityTypeOfLOan;
        this.riskScoring = riskScoring;
        this.cibilAccountStatus = cibilAccountStatus;
        this.numberOfEnquiresWithinMonth = numberOfEnquiresWithinMonth;
        this.numberOfOverdueLast24Months = numberOfOverdueLast24Months;
        this.CIRApproval = cIRApproval;
        this.DPDMoreThan30Days = dPDMoreThan30Days;
        this.noOfDPDOver60DaysinYears = noOfDPDOver60DaysinYears;
        this.elgibleLoanAmount = elgibleLoanAmount;
        this.existingEMIAmount = existingEMIAmount;
        this.borrowerLoanSanction = borrowerLoanSanction;
        this.creditRating = creditRating;
        this.typeOfCheckOff = typeOfCheckOff;
        this.typeofMCLR = typeofMCLR;
        this.spread = spread;
        this.interestRate = interestRate;
    }

    public Double getEMINMIRatio() {
        return EMINMIRatio;
    }

    public void setEMINMIRatio(Double eMINMIRatio) {
        EMINMIRatio = eMINMIRatio;
    }

    public Double getGMI() {
        return GMI;
    }

    public void setGMI(Double gMI) {
        GMI = gMI;
    }

    public Double getNMI() {
        return NMI;
    }

    public void setNMI(Double nMI) {
        NMI = nMI;
    }

    public Double getNMIGMIRatio() {
        return NMIGMIRatio;
    }

    public void setNMIGMIRatio(Double nMIGMIRatio) {
        NMIGMIRatio = nMIGMIRatio;
    }

    public Integer getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(Integer cibilScore) {
        this.cibilScore = cibilScore;
    }

    public String getCreditGrade() {
        return creditGrade;
    }

    public void setCreditGrade(String creditGrade) {
        this.creditGrade = creditGrade;
    }

    public Integer getRGScore() {
        return RGScore;
    }

    public void setRGScore(Integer rGScore) {
        RGScore = rGScore;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public Integer getExistingTopupDisbursedMonths() {
        return existingTopupDisbursedMonths;
    }

    public void setExistingTopupDisbursedMonths(Integer existingTopupDisbursedMonths) {
        this.existingTopupDisbursedMonths = existingTopupDisbursedMonths;
    }

    public String getIsXBSalaryAccount() {
        return isXBSalaryAccount;
    }

    public void setIsXBSalaryAccount(String isXBSalaryAccount) {
        this.isXBSalaryAccount = isXBSalaryAccount;
    }

    public String getModeOfRepayment() {
        return modeOfRepayment;
    }

    public void setModeOfRepayment(String modeOfRepayment) {
        this.modeOfRepayment = modeOfRepayment;
    }

    public Integer getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(Integer loanTenure) {
        this.loanTenure = loanTenure;
    }

    public Integer getNoOfXpressCreditTopups() {
        return noOfXpressCreditTopups;
    }

    public void setNoOfXpressCreditTopups(Integer noOfXpressCreditTopups) {
        this.noOfXpressCreditTopups = noOfXpressCreditTopups;
    }

    public Boolean getXpressCreditTopupAllowed() {
        return xpressCreditTopupAllowed;
    }

    public void setXpressCreditTopupAllowed(Boolean xpressCreditTopupAllowed) {
        this.xpressCreditTopupAllowed = xpressCreditTopupAllowed;
    }

    public Integer getSalaryAccountPeriod() {
        return salaryAccountPeriod;
    }

    public void setSalaryAccountPeriod(Integer salaryAccountPeriod) {
        this.salaryAccountPeriod = salaryAccountPeriod;
    }

    public String getSalaryAccountMandatory() {
        return salaryAccountMandatory;
    }

    public void setSalaryAccountMandatory(String salaryAccountMandatory) {
        this.salaryAccountMandatory = salaryAccountMandatory;
    }

    public Boolean getIsDisbursementPeriodtoCheck() {
        return isDisbursementPeriodtoCheck;
    }

    public void setIsDisbursementPeriodtoCheck(Boolean isDisbursementPeriodtoCheck) {
        this.isDisbursementPeriodtoCheck = isDisbursementPeriodtoCheck;
    }

    public String getFacilityTypeOfLOan() {
        return facilityTypeOfLOan;
    }

    public void setFacilityTypeOfLOan(String facilityTypeOfLOan) {
        this.facilityTypeOfLOan = facilityTypeOfLOan;
    }

    public Double getRiskScoring() {
        return riskScoring;
    }

    public void setRiskScoring(Double riskScoring) {
        this.riskScoring = riskScoring;
    }

    public String getCibilAccountStatus() {
        return cibilAccountStatus;
    }

    public void setCibilAccountStatus(String cibilAccountStatus) {
        this.cibilAccountStatus = cibilAccountStatus;
    }

    public Integer getNumberOfEnquiresWithinMonth() {
        return numberOfEnquiresWithinMonth;
    }

    public void setNumberOfEnquiresWithinMonth(Integer numberOfEnquiresWithinMonth) {
        this.numberOfEnquiresWithinMonth = numberOfEnquiresWithinMonth;
    }

    public Integer getNumberOfOverdueLast24Months() {
        return numberOfOverdueLast24Months;
    }

    public void setNumberOfOverdueLast24Months(Integer numberOfOverdueLast24Months) {
        this.numberOfOverdueLast24Months = numberOfOverdueLast24Months;
    }

    public String getCIRApproval() {
        return CIRApproval;
    }

    public void setCIRApproval(String cIRApproval) {
        CIRApproval = cIRApproval;
    }

    public boolean isDPDMoreThan30Days() {
        return DPDMoreThan30Days;
    }

    public void setDPDMoreThan30Days(boolean dPDMoreThan30Days) {
        DPDMoreThan30Days = dPDMoreThan30Days;
    }

    public Integer getNoOfDPDOver60DaysinYears() {
        return noOfDPDOver60DaysinYears;
    }

    public void setNoOfDPDOver60DaysinYears(Integer noOfDPDOver60DaysinYears) {
        this.noOfDPDOver60DaysinYears = noOfDPDOver60DaysinYears;
    }

    public Double getElgibleLoanAmount() {
        return elgibleLoanAmount;
    }

    public void setElgibleLoanAmount(Double elgibleLoanAmount) {
        this.elgibleLoanAmount = elgibleLoanAmount;
    }

    public Double getExistingEMIAmount() {
        return existingEMIAmount;
    }

    public void setExistingEMIAmount(Double existingEMIAmount) {
        this.existingEMIAmount = existingEMIAmount;
    }

    public String getBorrowerLoanSanction() {
        return borrowerLoanSanction;
    }

    public void setBorrowerLoanSanction(String borrowerLoanSanction) {
        this.borrowerLoanSanction = borrowerLoanSanction;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public String getTypeOfCheckOff() {
        return typeOfCheckOff;
    }

    public void setTypeOfCheckOff(String typeOfCheckOff) {
        this.typeOfCheckOff = typeOfCheckOff;
    }

    public String getTypeofMCLR() {
        return typeofMCLR;
    }

    public void setTypeofMCLR(String typeofMCLR) {
        this.typeofMCLR = typeofMCLR;
    }

    public Character getSpread() {
        return spread;
    }

    public void setSpread(Character spread) {
        this.spread = spread;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
  
 
}
