package org.drools.demo.rest;

import static org.drools.demo.rest.Constants.*;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class OrganizationalUnit {
    
    void getAll() throws IOException {
        System.out.println(ORGANIZATION_UNIT_GET);
        WebClient wc = WebClient.create(ORGANIZATION_UNIT_GET);
        wc.header("Authorization", authorizationHeader);
        Response resp = wc.get();
        System.out.println(IOUtils.toString((InputStream) resp.getEntity()));
        wc.close();
    }
    
    void post() throws IOException {
        System.out.println(ORGANIZATION_UNIT_POST);
        WebClient wc = WebClient.create(ORGANIZATION_UNIT_POST);
        wc.header("Authorization", authorizationHeader);
        Response resp = wc.post("{\"name\":\"helloWorldUnit\",\"owner\":\"tester\",\"description\":null,\"repositories\":[]}");
        System.out.println(IOUtils.toString((InputStream) resp.getEntity()));
        wc.close();
    }

    public static void main(String[] args) throws IOException {

        OrganizationalUnit ou = new OrganizationalUnit();
        ou.getAll();
        ou.post();
    }

}
