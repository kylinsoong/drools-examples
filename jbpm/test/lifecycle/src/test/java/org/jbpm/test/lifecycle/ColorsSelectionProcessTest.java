/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jbpm.test.lifecycle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.impl.ClassPathResource;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author esteban
 */
public class ColorsSelectionProcessTest extends BaseTest implements Serializable {

    private KnowledgeRuntimeLogger fileLogger;
    private StatefulKnowledgeSession ksession;
    private SyncWSHumanTaskHandler humanTaskHandler;
    
    @Before
    public void setup() throws IOException{
        this.ksession = this.createKSession();
        
        //Console log. Try to analyze it first
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
        
        //File logger: try to open its output using Audit View in eclipse
        File logFile = File.createTempFile("process-output", "");
        System.out.println("Log file= "+logFile.getAbsolutePath()+".log");
        fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());
        
        //Configure Sync WIHandler for Human Tasks
        humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        
        this.ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        
    }

    @After
    public void cleanup(){
        if (this.fileLogger != null){
            this.fileLogger.close();
        }
    } 
    
    @Test
    public void noColorTest() throws InterruptedException, IOException{
        
        List<String> createdColors = new ArrayList<String>();
        Map<String, Object> inputParameters = new HashMap<String, Object>();
        inputParameters.put("createdColors", createdColors);
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.plugtree.training.jbpm.colorselectionprocess", inputParameters);
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        //krisv had 1 task. (see the definition of the porcess)
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        
        TaskSummary krisvsTask = results.get(0);
        
        //krisv completes the task without selecting any color
        localTaskService.start(krisvsTask.getId(), "krisv");
        
        ContentData resultData = this.createColorSelectionData(null, 50.0);
        localTaskService.complete(krisvsTask.getId(), "krisv", resultData );
        
        //The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //One color was created: black
        Assert.assertEquals(1, createdColors.size());
        Assert.assertTrue(createdColors.contains("black"));
        
    }
    
    @Test
    public void oneColorTest() throws InterruptedException, IOException{
        
        List<String> createdColors = new ArrayList<String>();
        Map<String, Object> inputParameters = new HashMap<String, Object>();
        inputParameters.put("createdColors", createdColors);
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.plugtree.training.jbpm.colorselectionprocess", inputParameters);
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        //krisv had 1 task. (see the definition of the porcess)
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        
        TaskSummary krisvsTask = results.get(0);
        
        //krisv completes the task selecting 2 colors: red and green with 50% alpha
        localTaskService.start(krisvsTask.getId(), "krisv");
        
        
        ContentData resultData = this.createColorSelectionData(new String[]{"red", "green"}, 50.0);
        localTaskService.complete(krisvsTask.getId(), "krisv", resultData );
        
        //The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //One color was created: yellow
        Assert.assertEquals(1, createdColors.size());
        Assert.assertTrue(createdColors.contains("yellow - 50.0%"));
        
    }
    
    @Test
    public void threeColorsTest() throws InterruptedException, IOException{
        
        List<String> createdColors = new ArrayList<String>();
        Map<String, Object> inputParameters = new HashMap<String, Object>();
        inputParameters.put("createdColors", createdColors);
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.plugtree.training.jbpm.colorselectionprocess", inputParameters);
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        //krisv had 1 task. (see the definition of the porcess)
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        
        TaskSummary krisvsTask = results.get(0);
        
        //krisv completes the task selecting 3 colors: red, green and blue with 75% alpha
        localTaskService.start(krisvsTask.getId(), "krisv");
        
        
        ContentData resultData = this.createColorSelectionData(new String[]{"red", "green", "blue"}, 75.0);
        localTaskService.complete(krisvsTask.getId(), "krisv", resultData );
        
        //The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
        
        //Three colors were created: yellow, magenta and cyan
        Assert.assertEquals(3, createdColors.size());
        Assert.assertTrue(createdColors.contains("yellow - 75.0%"));
        Assert.assertTrue(createdColors.contains("magenta - 75.0%"));
        Assert.assertTrue(createdColors.contains("cyan - 75.0%"));
        
    }
    
    private ContentData createColorSelectionData(String[] colors, double alpha) throws IOException{
        
        //put the 2 colors in a List
        List<String> selectedColors = new ArrayList<String>();
        if (colors != null){
            selectedColors.addAll(Arrays.asList(colors));
        }
        
        //add the list to the result map and also the alpha
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("selectedColorsResult", selectedColors);
        result.put("selectedAlphaResult", alpha);
        
        //serialize the result map
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        
        oos.writeObject(result);
        
        //create and fill the response ContentData object
        ContentData content = new ContentData();
        content.setAccessType(AccessType.Inline);
        content.setContent(baos.toByteArray());
        
        return content;
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add colorsSelectionProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/colorsSelectionProcess.bpmn"), ResourceType.BPMN2);
        System.out.println("Compiling resources");
        
        //Check for errors
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                    System.out.println("Error building kbase: " + error.getMessage());
                }
            }
            throw new RuntimeException("Error building kbase!");
        }

        //Create a knowledge base and add the generated package
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        //return a new statefull session
        return kbase.newStatefulKnowledgeSession();
    }
    
}
