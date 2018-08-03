package org.drools.demo.rest;

import org.apache.cxf.common.util.Base64Utility;

/**
 * http://localhost:8080/business-central/rest-api.jsp
 * @author kylin
 *
 */
public interface Constants {
    
    String APP_URL = "http://localhost:8080/business-central/rest/";
    
    String USER = "kylin";
    String COLON = ":";
    String PASSWORD = "password1!";
    String authorizationHeader = "Basic " + Base64Utility.encode((USER + COLON + PASSWORD).getBytes());
    
    String ORGANIZATION_UNIT_GET = APP_URL + "organizationalunits";
    String ORGANIZATION_UNIT_POST = APP_URL + "organizationalunits";


}
