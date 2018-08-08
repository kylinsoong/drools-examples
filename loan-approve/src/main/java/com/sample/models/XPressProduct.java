package com.sample.models;

import java.io.Serializable;
import org.kie.api.definition.type.Label;

public class XPressProduct implements Serializable {
    
    private static final long serialVersionUID = 7066493396450699932L;
    
    @Label("Loan Scehme")
    private String loanScehme;
  
    @Label("RejectionReason")
    private String rejectionReason;
  
    @Label("RejectedLoanProduct")
    private Boolean rejectedLoanProduct;
  
    @Label("MaxLA")
    private Double maxLA;
  
    @Label("MinLA")
    private Double minLA;
  
  
    public XPressProduct() {}


    public XPressProduct(String loanScehme, String rejectionReason, Boolean rejectedLoanProduct, Double maxLA,
            Double minLA) {
        super();
        this.loanScehme = loanScehme;
        this.rejectionReason = rejectionReason;
        this.rejectedLoanProduct = rejectedLoanProduct;
        this.maxLA = maxLA;
        this.minLA = minLA;
    }


    public String getLoanScehme() {
        return loanScehme;
    }


    public void setLoanScehme(String loanScehme) {
        this.loanScehme = loanScehme;
    }


    public String getRejectionReason() {
        return rejectionReason;
    }


    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }


    public Boolean getRejectedLoanProduct() {
        return rejectedLoanProduct;
    }


    public void setRejectedLoanProduct(Boolean rejectedLoanProduct) {
        this.rejectedLoanProduct = rejectedLoanProduct;
    }


    public Double getMaxLA() {
        return maxLA;
    }


    public void setMaxLA(Double maxLA) {
        this.maxLA = maxLA;
    }


    public Double getMinLA() {
        return minLA;
    }


    public void setMinLA(Double minLA) {
        this.minLA = minLA;
    }
  

}
