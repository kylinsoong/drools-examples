package com.sample.models;

import java.io.Serializable;
import org.kie.api.definition.type.Label;

public class BuisnessExceptions implements Serializable {

    private static final long serialVersionUID = -4505090213518455468L;

    @Label("Exception")
    private String exception;
  
    @Label("Exception")
    private String reason;
  
    public BuisnessExceptions() {}

    public BuisnessExceptions(String exception, String reason) {
        super();
        this.exception = exception;
        this.reason = reason;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
  

}
