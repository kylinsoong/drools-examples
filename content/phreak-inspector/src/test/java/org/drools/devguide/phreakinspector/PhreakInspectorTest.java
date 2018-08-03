/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.devguide.phreakinspector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;
import org.drools.devguide.phreakinspector.model.PhreakInspector;

/**
 *
 * @author esteban
 */
public class PhreakInspectorTest {

    @Test
    public void doTest() throws IOException {

        Map<Resource, ResourceType> resources = new LinkedHashMap<>();

        resources.put(ResourceFactory.newClassPathResource("rules04/accumulation-rules.drl"), ResourceType.DRL);

        PhreakInspector inspector = new PhreakInspector();

        InputStream graphViz = inspector.fromResources(resources);

//        this.writeTmpFile(graphViz);
        this.writeToFixedTmpFile(graphViz);
    }

    private void writeTmpFile(InputStream is) throws IOException {
        try (FileWriter fileWriter = new FileWriter(File.createTempFile("phreak-", ".dot"))) {
            IOUtils.write(IOUtils.toByteArray(is), fileWriter);
        }
    }
    
    private void writeToFixedTmpFile(InputStream is) throws IOException {
        try (FileWriter fileWriter = new FileWriter(new File("/tmp","phreak.dot"))) {
            IOUtils.write(IOUtils.toByteArray(is), fileWriter);
        }
    }

}
