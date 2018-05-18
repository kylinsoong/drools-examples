package org.sample.sinotrans.models;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wfobc_transport_term",
    "wfobc_etd",
    "wfobc_payment_mode",
    "wfobc_total_volumn",
    "wfobc_bok_contract_no",
    "wfobc_consign_department",
    "wfobc_load_port",
    "wfobc_notify",
    "wfobc_booking_remark",
    "wfobc_voyage",
    "wfobc_booking_party",
    "wfobc_discharge_port",
    "org_id",
    "wfobc_vessel_name",
    "wfobc_shipper",
    "wfobc_consignee",
    "order_id",
    "wfobc_custom_no",
    "wfobc_total_gross_weight",
    "ext_company"
})
public class Summary {

    @JsonProperty("wfobc_transport_term")
    private String wfobcTransportTerm;
    
    @JsonProperty("wfobc_etd")
    private String wfobcEtd;
    
    @JsonProperty("wfobc_payment_mode")
    private String wfobcPaymentMode;
    
    @JsonProperty("wfobc_total_volumn")
    private String wfobcTotalVolumn;
    
    @JsonProperty("wfobc_bok_contract_no")
    private String wfobcBokContractNo;
    
    @JsonProperty("wfobc_consign_department")
    private String wfobcConsignDepartment;
    
    @JsonProperty("wfobc_load_port")
    private String wfobcLoadPort;
    
    @JsonProperty("wfobc_notify")
    private String wfobcNotify;
    
    @JsonProperty("wfobc_booking_remark")
    private String wfobcBookingRemark;
    
    @JsonProperty("wfobc_voyage")
    private String wfobcVoyage;
    
    @JsonProperty("wfobc_booking_party")
    private String wfobcBookingParty;
    
    @JsonProperty("wfobc_discharge_port")
    private String wfobcDischargePort;
    
    @JsonProperty("org_id")
    private String orgId;
    
    @JsonProperty("wfobc_vessel_name")
    private String wfobcVesselName;
    
    @JsonProperty("wfobc_shipper")
    private String wfobcShipper;
    
    @JsonProperty("wfobc_consignee")
    private String wfobcConsignee;
    
    @JsonProperty("order_id")
    private String orderId;
    
    @JsonProperty("wfobc_custom_no")
    private String wfobcCustomNo;
    
    @JsonProperty("wfobc_total_gross_weight")
    private String wfobcTotalGrossWeight;
    
    @JsonProperty("ext_company")
    private String extCompany;
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("wfobc_transport_term")
    public String getWfobcTransportTerm() {
        return wfobcTransportTerm;
    }

    @JsonProperty("wfobc_transport_term")
    public void setWfobcTransportTerm(String wfobcTransportTerm) {
        this.wfobcTransportTerm = wfobcTransportTerm;
    }

    @JsonProperty("wfobc_etd")
    public String getWfobcEtd() {
        return wfobcEtd;
    }

    @JsonProperty("wfobc_etd")
    public void setWfobcEtd(String wfobcEtd) {
        this.wfobcEtd = wfobcEtd;
    }

    @JsonProperty("wfobc_payment_mode")
    public String getWfobcPaymentMode() {
        return wfobcPaymentMode;
    }

    @JsonProperty("wfobc_payment_mode")
    public void setWfobcPaymentMode(String wfobcPaymentMode) {
        this.wfobcPaymentMode = wfobcPaymentMode;
    }

    @JsonProperty("wfobc_total_volumn")
    public String getWfobcTotalVolumn() {
        return wfobcTotalVolumn;
    }

    @JsonProperty("wfobc_total_volumn")
    public void setWfobcTotalVolumn(String wfobcTotalVolumn) {
        this.wfobcTotalVolumn = wfobcTotalVolumn;
    }

    @JsonProperty("wfobc_bok_contract_no")
    public String getWfobcBokContractNo() {
        return wfobcBokContractNo;
    }

    @JsonProperty("wfobc_bok_contract_no")
    public void setWfobcBokContractNo(String wfobcBokContractNo) {
        this.wfobcBokContractNo = wfobcBokContractNo;
    }

    @JsonProperty("wfobc_consign_department")
    public String getWfobcConsignDepartment() {
        return wfobcConsignDepartment;
    }

    @JsonProperty("wfobc_consign_department")
    public void setWfobcConsignDepartment(String wfobcConsignDepartment) {
        this.wfobcConsignDepartment = wfobcConsignDepartment;
    }

    @JsonProperty("wfobc_load_port")
    public String getWfobcLoadPort() {
        return wfobcLoadPort;
    }

    @JsonProperty("wfobc_load_port")
    public void setWfobcLoadPort(String wfobcLoadPort) {
        this.wfobcLoadPort = wfobcLoadPort;
    }

    @JsonProperty("wfobc_notify")
    public String getWfobcNotify() {
        return wfobcNotify;
    }

    @JsonProperty("wfobc_notify")
    public void setWfobcNotify(String wfobcNotify) {
        this.wfobcNotify = wfobcNotify;
    }

    @JsonProperty("wfobc_booking_remark")
    public String getWfobcBookingRemark() {
        return wfobcBookingRemark;
    }

    @JsonProperty("wfobc_booking_remark")
    public void setWfobcBookingRemark(String wfobcBookingRemark) {
        this.wfobcBookingRemark = wfobcBookingRemark;
    }

    @JsonProperty("wfobc_voyage")
    public String getWfobcVoyage() {
        return wfobcVoyage;
    }

    @JsonProperty("wfobc_voyage")
    public void setWfobcVoyage(String wfobcVoyage) {
        this.wfobcVoyage = wfobcVoyage;
    }

    @JsonProperty("wfobc_booking_party")
    public String getWfobcBookingParty() {
        return wfobcBookingParty;
    }

    @JsonProperty("wfobc_booking_party")
    public void setWfobcBookingParty(String wfobcBookingParty) {
        this.wfobcBookingParty = wfobcBookingParty;
    }

    @JsonProperty("wfobc_discharge_port")
    public String getWfobcDischargePort() {
        return wfobcDischargePort;
    }

    @JsonProperty("wfobc_discharge_port")
    public void setWfobcDischargePort(String wfobcDischargePort) {
        this.wfobcDischargePort = wfobcDischargePort;
    }

    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @JsonProperty("wfobc_vessel_name")
    public String getWfobcVesselName() {
        return wfobcVesselName;
    }

    @JsonProperty("wfobc_vessel_name")
    public void setWfobcVesselName(String wfobcVesselName) {
        this.wfobcVesselName = wfobcVesselName;
    }

    @JsonProperty("wfobc_shipper")
    public String getWfobcShipper() {
        return wfobcShipper;
    }

    @JsonProperty("wfobc_shipper")
    public void setWfobcShipper(String wfobcShipper) {
        this.wfobcShipper = wfobcShipper;
    }

    @JsonProperty("wfobc_consignee")
    public String getWfobcConsignee() {
        return wfobcConsignee;
    }

    @JsonProperty("wfobc_consignee")
    public void setWfobcConsignee(String wfobcConsignee) {
        this.wfobcConsignee = wfobcConsignee;
    }

    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("wfobc_custom_no")
    public String getWfobcCustomNo() {
        return wfobcCustomNo;
    }

    @JsonProperty("wfobc_custom_no")
    public void setWfobcCustomNo(String wfobcCustomNo) {
        this.wfobcCustomNo = wfobcCustomNo;
    }

    @JsonProperty("wfobc_total_gross_weight")
    public String getWfobcTotalGrossWeight() {
        return wfobcTotalGrossWeight;
    }

    @JsonProperty("wfobc_total_gross_weight")
    public void setWfobcTotalGrossWeight(String wfobcTotalGrossWeight) {
        this.wfobcTotalGrossWeight = wfobcTotalGrossWeight;
    }

    @JsonProperty("ext_company")
    public String getExtCompany() {
        return extCompany;
    }

    @JsonProperty("ext_company")
    public void setExtCompany(String extCompany) {
        this.extCompany = extCompany;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
