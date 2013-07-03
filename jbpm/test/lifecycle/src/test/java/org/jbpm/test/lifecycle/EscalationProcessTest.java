package org.jbpm.test.lifecycle;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import org.jbpm.task.OrganizationalEntity;
import org.jbpm.task.Task;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.DefaultEscalatedDeadlineHandler;
import org.jbpm.task.service.EscalatedDeadlineHandler;
import org.jbpm.test.lifecycle.workitem.SyncDeadlineEnabledWSHumanTaskHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.subethamail.wiser.Wiser;

public class EscalationProcessTest extends BaseTest implements Serializable {

	private static final long serialVersionUID = 7995728433691888320L;
	
	private KnowledgeRuntimeLogger fileLogger;
    private StatefulKnowledgeSession ksession;
    
    private Wiser wiser;
    
    private DefaultEscalatedDeadlineHandler defaultEscalatedDeadlineHandler;
    private SyncDeadlineEnabledWSHumanTaskHandler humanTaskHandler;
    
    @Before
    public void setup() throws IOException{
    	
        this.ksession = this.createKSession();
        
        //Console log. Try to analyze it first
        KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
        
        //File logger: try to open its output using Audit View in eclipse
        File logFile = File.createTempFile("process-output", "");
        System.out.println("Log file= "+logFile.getAbsolutePath()+".log");
        fileLogger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,logFile.getAbsolutePath());
        
        //Configure Sync WIHandler for Human Tasks that supports deadlines
        //(check its implementation)
        humanTaskHandler = new SyncDeadlineEnabledWSHumanTaskHandler(localTaskService, ksession);
        humanTaskHandler.setLocal(true);
        humanTaskHandler.connect();
        
        this.ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
        
        ((DefaultEscalatedDeadlineHandler)this.getEscalatedDeadlineHandler()).setManager(this.ksession.getWorkItemManager());
        
        //Configure a smtp server
        wiser = new Wiser();
        wiser.setHostname("localhost");
        wiser.setPort(1125);
        wiser.start();
        
    }

    @After
    public void cleanup(){
    	
		if (this.fileLogger != null) {
			this.fileLogger.close();
		}

		if (wiser != null) {
			wiser.stop();
		}
	}
    
    @Test
    public void taskEscalationTest() throws InterruptedException, MessagingException, IOException{
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("Task_A_Deadline_Time", 3000L); 
        parameters.put("Task_A_Deadline_Notification_Recipients", "Steve Rogers,Bruce Wayne"); 
        parameters.put("Task_A_Deadline_Reassignment_Potential_Owners", "Steve Rogers,Bruce Wayne"); 
        
        //Start the process using its id
        ProcessInstance process = ksession.startProcess("org.jbpm.test.escalationprocess",parameters);
        
        Assert.assertEquals(ProcessInstance.STATE_ACTIVE, process.getState());
        
        Thread.sleep(500);
        
        //krisv has one potencial task
        List<TaskSummary> results = localTaskService.getTasksAssignedAsPotentialOwner("krisv", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());

        Thread.sleep(500);
        
        //No notification yet
        println("wiser.getMessages().isEmpty(): " + wiser.getMessages().isEmpty());
        Assert.assertTrue(wiser.getMessages().isEmpty());
        
        //No Reassignment yet
        results = localTaskService.getTasksAssignedAsPotentialOwner("Steve Rogers", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertTrue(results.isEmpty());
       
		println("Sleeping");
 
        Thread.sleep(3000);

		println("Coming back");
        
        //3 Notifications
        Assert.assertTrue(wiser.getMessages().size() > 1);
        MimeMessage msg =  wiser.getMessages().get(0).getMimeMessage();
        Assert.assertEquals( "Task A reaches its deadline", msg.getSubject() );
        Assert.assertEquals( "taskNotification@system.com", ((InternetAddress)msg.getFrom()[0]).getAddress() );       
        Assert.assertEquals( "admin@gmail.com", ((InternetAddress)msg.getRecipients( RecipientType.TO )[0]).getAddress() );
        Assert.assertEquals( "steve.rogers@gmail.com", ((InternetAddress)msg.getRecipients( RecipientType.TO )[1]).getAddress() );
        Assert.assertEquals( "bruce.wayne@gmail.com", ((InternetAddress)msg.getRecipients( RecipientType.TO )[2]).getAddress() );
        
        //The task was Reassigned
        results = localTaskService.getTasksAssignedAsPotentialOwner("Steve Rogers", "en-UK");
        Assert.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        TaskSummary stevesTask = results.get(0);
        
        Task task = localTaskService.getTask(stevesTask.getId());
        
        //steve claims the task
        localTaskService.claim(stevesTask.getId(), "Steve Rogers");
        
        //steve completes the task
        localTaskService.start(stevesTask.getId(), "Steve Rogers");
        localTaskService.complete(stevesTask.getId(), "Steve Rogers", null);
        
        Thread.sleep(2000);
        
        //The process should be completed
        Assert.assertEquals(ProcessInstance.STATE_COMPLETED, process.getState());
    }
    
    /**
     * Creates a ksession from a kbase containing process definition
     * @return 
     */
    public StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/escalationSampleProcess.bpmn"), ResourceType.BPMN2);
        println("Compiling resources");
        
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

    @Override
    public EscalatedDeadlineHandler getEscalatedDeadlineHandler() {
    	
        if (this.defaultEscalatedDeadlineHandler != null){
            return this.defaultEscalatedDeadlineHandler;
        }
        Map<OrganizationalEntity,String> languages = new HashMap<OrganizationalEntity, String>();
        languages.put(users.get("Administrator"), "en-UK");
        languages.put(users.get("tony"), "en-UK");
        languages.put(users.get("john"), "en-UK");
        languages.put(users.get("steve"), "en-UK");
        languages.put(users.get("bruce"), "en-UK");
        userInfo.setLanguages(languages);
        
        Map<OrganizationalEntity,String> emails = new HashMap<OrganizationalEntity, String>();
        emails.put(users.get("Administrator"), "admin@gmail.com");
        emails.put(users.get("tony"), "tony.stark@gmail.com");
        emails.put(users.get("john"), "john@gmail.com");
        emails.put(users.get("steve"), "steve.rogers@gmail.com");
        emails.put(users.get("bruce"), "bruce.wayne@gmail.com");
        
        defaultEscalatedDeadlineHandler = new DefaultEscalatedDeadlineHandler(conf);
        userInfo.setEmails(emails);
        defaultEscalatedDeadlineHandler.setUserInfo(userInfo);
        return defaultEscalatedDeadlineHandler;
    }
    
    
}
