package org.drools.demo.rest;

import org.apache.cxf.common.util.Base64Utility;

public class Main {
    
    static String USER = "rhdmAdmin";
    static String COLON = ":";
    static String PASSWORD = "password1!";
    static String authorizationHeader = "Basic " + Base64Utility.encode((USER + COLON + PASSWORD).getBytes());
    

    public static void main(String[] args) {

        System.out.println(authorizationHeader);
    }

}
