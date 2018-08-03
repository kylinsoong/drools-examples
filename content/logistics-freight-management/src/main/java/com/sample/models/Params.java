package com.sample.models;

import java.io.Serializable;
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
    "docType",
    "appId",
    "appKey",
    "appSecret",
    "echoStr",
    "reqUuid",
    "ifNeedOcr",
    "ocrType",
    "billStyle",
    "ifNeedCallback",
    "callbackUrl",
    "pathCode",
    "fieldStyle"
})
public class Params implements Serializable {

    private static final long serialVersionUID = -946949010165481447L;
    
    @JsonProperty("docType")
    private String docType;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("appKey")
    private String appKey;
    @JsonProperty("appSecret")
    private String appSecret;
    @JsonProperty("echoStr")
    private String echoStr;
    @JsonProperty("reqUuid")
    private String reqUuid;
    @JsonProperty("ifNeedOcr")
    private String ifNeedOcr;
    @JsonProperty("ocrType")
    private String ocrType;
    @JsonProperty("billStyle")
    private String billStyle;
    @JsonProperty("ifNeedCallback")
    private String ifNeedCallback;
    @JsonProperty("callbackUrl")
    private String callbackUrl;
    @JsonProperty("pathCode")
    private String pathCode;
    @JsonProperty("fieldStyle")
    private String fieldStyle;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("docType")
    public String getDocType() {
        return docType;
    }

    @JsonProperty("docType")
    public void setDocType(String docType) {
        this.docType = docType;
    }

    @JsonProperty("appId")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("appId")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @JsonProperty("appKey")
    public String getAppKey() {
        return appKey;
    }

    @JsonProperty("appKey")
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @JsonProperty("appSecret")
    public String getAppSecret() {
        return appSecret;
    }

    @JsonProperty("appSecret")
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @JsonProperty("echoStr")
    public String getEchoStr() {
        return echoStr;
    }

    @JsonProperty("echoStr")
    public void setEchoStr(String echoStr) {
        this.echoStr = echoStr;
    }

    @JsonProperty("reqUuid")
    public String getReqUuid() {
        return reqUuid;
    }

    @JsonProperty("reqUuid")
    public void setReqUuid(String reqUuid) {
        this.reqUuid = reqUuid;
    }

    @JsonProperty("ifNeedOcr")
    public String getIfNeedOcr() {
        return ifNeedOcr;
    }

    @JsonProperty("ifNeedOcr")
    public void setIfNeedOcr(String ifNeedOcr) {
        this.ifNeedOcr = ifNeedOcr;
    }

    @JsonProperty("ocrType")
    public String getOcrType() {
        return ocrType;
    }

    @JsonProperty("ocrType")
    public void setOcrType(String ocrType) {
        this.ocrType = ocrType;
    }

    @JsonProperty("billStyle")
    public String getBillStyle() {
        return billStyle;
    }

    @JsonProperty("billStyle")
    public void setBillStyle(String billStyle) {
        this.billStyle = billStyle;
    }

    @JsonProperty("ifNeedCallback")
    public String getIfNeedCallback() {
        return ifNeedCallback;
    }

    @JsonProperty("ifNeedCallback")
    public void setIfNeedCallback(String ifNeedCallback) {
        this.ifNeedCallback = ifNeedCallback;
    }

    @JsonProperty("callbackUrl")
    public Object getCallbackUrl() {
        return callbackUrl;
    }

    @JsonProperty("callbackUrl")
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @JsonProperty("pathCode")
    public String getPathCode() {
        return pathCode;
    }

    @JsonProperty("pathCode")
    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    @JsonProperty("fieldStyle")
    public String getFieldStyle() {
        return fieldStyle;
    }

    @JsonProperty("fieldStyle")
    public void setFieldStyle(String fieldStyle) {
        this.fieldStyle = fieldStyle;
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
