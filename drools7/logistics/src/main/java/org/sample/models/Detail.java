package org.sample.models;

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
    "wfobcc_description",
    "wfobcc_volumn_unit",
    "wfobcc_hscode",
    "wfobcc_quantity",
    "wfobcc_marks",
    "wfobcc_volumn",
    "wfobcc_pcs",
    "wfobcc_gross_weight",
    "wfobcc_gross_weight_unit"
})
public class Detail {

    @JsonProperty("wfobcc_description")
    private String wfobccDescription;
    @JsonProperty("wfobcc_volumn_unit")
    private String wfobccVolumnUnit;
    @JsonProperty("wfobcc_hscode")
    private String wfobccHscode;
    @JsonProperty("wfobcc_quantity")
    private String wfobccQuantity;
    @JsonProperty("wfobcc_marks")
    private String wfobccMarks;
    @JsonProperty("wfobcc_volumn")
    private String wfobccVolumn;
    @JsonProperty("wfobcc_pcs")
    private String wfobccPcs;
    @JsonProperty("wfobcc_gross_weight")
    private String wfobccGrossWeight;
    @JsonProperty("wfobcc_gross_weight_unit")
    private String wfobccGrossWeightUnit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("wfobcc_description")
    public String getWfobccDescription() {
        return wfobccDescription;
    }

    @JsonProperty("wfobcc_description")
    public void setWfobccDescription(String wfobccDescription) {
        this.wfobccDescription = wfobccDescription;
    }

    @JsonProperty("wfobcc_volumn_unit")
    public String getWfobccVolumnUnit() {
        return wfobccVolumnUnit;
    }

    @JsonProperty("wfobcc_volumn_unit")
    public void setWfobccVolumnUnit(String wfobccVolumnUnit) {
        this.wfobccVolumnUnit = wfobccVolumnUnit;
    }

    @JsonProperty("wfobcc_hscode")
    public String getWfobccHscode() {
        return wfobccHscode;
    }

    @JsonProperty("wfobcc_hscode")
    public void setWfobccHscode(String wfobccHscode) {
        this.wfobccHscode = wfobccHscode;
    }

    @JsonProperty("wfobcc_quantity")
    public String getWfobccQuantity() {
        return wfobccQuantity;
    }

    @JsonProperty("wfobcc_quantity")
    public void setWfobccQuantity(String wfobccQuantity) {
        this.wfobccQuantity = wfobccQuantity;
    }

    @JsonProperty("wfobcc_marks")
    public String getWfobccMarks() {
        return wfobccMarks;
    }

    @JsonProperty("wfobcc_marks")
    public void setWfobccMarks(String wfobccMarks) {
        this.wfobccMarks = wfobccMarks;
    }

    @JsonProperty("wfobcc_volumn")
    public String getWfobccVolumn() {
        return wfobccVolumn;
    }

    @JsonProperty("wfobcc_volumn")
    public void setWfobccVolumn(String wfobccVolumn) {
        this.wfobccVolumn = wfobccVolumn;
    }

    @JsonProperty("wfobcc_pcs")
    public String getWfobccPcs() {
        return wfobccPcs;
    }

    @JsonProperty("wfobcc_pcs")
    public void setWfobccPcs(String wfobccPcs) {
        this.wfobccPcs = wfobccPcs;
    }

    @JsonProperty("wfobcc_gross_weight")
    public String getWfobccGrossWeight() {
        return wfobccGrossWeight;
    }

    @JsonProperty("wfobcc_gross_weight")
    public void setWfobccGrossWeight(String wfobccGrossWeight) {
        this.wfobccGrossWeight = wfobccGrossWeight;
    }

    @JsonProperty("wfobcc_gross_weight_unit")
    public String getWfobccGrossWeightUnit() {
        return wfobccGrossWeightUnit;
    }

    @JsonProperty("wfobcc_gross_weight_unit")
    public void setWfobccGrossWeightUnit(String wfobccGrossWeightUnit) {
        this.wfobccGrossWeightUnit = wfobccGrossWeightUnit;
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
