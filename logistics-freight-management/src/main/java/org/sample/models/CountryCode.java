package org.sample.models;

/**
 * https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3
 * @author kylin
 *
 */
public class CountryCode {
    
    private String port;
    
    private String code;

    public CountryCode(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
