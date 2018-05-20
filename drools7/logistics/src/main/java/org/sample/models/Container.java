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
    "wfobcc_container_type",
    "wfobcc_container_num"
})
public class Container {

    @JsonProperty("wfobcc_container_type")
    private String wfobccContainerType;
    @JsonProperty("wfobcc_container_num")
    private String wfobccContainerNum;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("wfobcc_container_type")
    public String getWfobccContainerType() {
        return wfobccContainerType;
    }

    @JsonProperty("wfobcc_container_type")
    public void setWfobccContainerType(String wfobccContainerType) {
        this.wfobccContainerType = wfobccContainerType;
    }

    @JsonProperty("wfobcc_container_num")
    public String getWfobccContainerNum() {
        return wfobccContainerNum;
    }

    @JsonProperty("wfobcc_container_num")
    public void setWfobccContainerNum(String wfobccContainerNum) {
        this.wfobccContainerNum = wfobccContainerNum;
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
