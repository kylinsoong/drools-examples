package com.sample.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Summary",
    "Container",
    "pages",
    "Detail"
})
public class Datum implements Serializable {

    private static final long serialVersionUID = 9035828373172353364L;
    
    @JsonProperty("Summary")
    private Summary summary;
    @JsonProperty("Container")
    private List<Container> container = null;
    @JsonProperty("pages")
    private Pages pages;
    @JsonProperty("Detail")
    private List<Detail> detail = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Summary")
    public Summary getSummary() {
        return summary;
    }

    @JsonProperty("Summary")
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @JsonProperty("Container")
    public List<Container> getContainer() {
        return container;
    }

    @JsonProperty("Container")
    public void setContainer(List<Container> container) {
        this.container = container;
    }

    @JsonProperty("pages")
    public Pages getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Pages pages) {
        this.pages = pages;
    }

    @JsonProperty("Detail")
    public List<Detail> getDetail() {
        return detail;
    }

    @JsonProperty("Detail")
    public void setDetail(List<Detail> detail) {
        this.detail = detail;
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
