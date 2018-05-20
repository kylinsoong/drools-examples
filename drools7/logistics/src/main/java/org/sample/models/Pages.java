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
    "endpage",
    "startpage",
    "fileName"
})
public class Pages {

    @JsonProperty("endpage")
    private Integer endpage;
    @JsonProperty("startpage")
    private Integer startpage;
    @JsonProperty("fileName")
    private String fileName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("endpage")
    public Integer getEndpage() {
        return endpage;
    }

    @JsonProperty("endpage")
    public void setEndpage(Integer endpage) {
        this.endpage = endpage;
    }

    @JsonProperty("startpage")
    public Integer getStartpage() {
        return startpage;
    }

    @JsonProperty("startpage")
    public void setStartpage(Integer startpage) {
        this.startpage = startpage;
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
