package com.sample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.Globals;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.wsht.AsyncWSHumanTaskHandler;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.AccessType;
import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.hornetq.CommandBasedHornetQWSHumanTaskHandler;
import org.jbpm.task.service.hornetq.HornetQTaskClientConnector;
import org.jbpm.task.service.hornetq.HornetQTaskClientHandler;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;

//import bitronix.tm.BitronixTransactionManager;
//import bitronix.tm.TransactionManagerServices;

	public class ProcessWorkflow {
		
		
		//private static final String PROCESS_NAME = "OrderWorkflowDemo.OrderApprovalProcess";
		private static final String PROCESS_NAME = "SubprocessTest.MyMainProcess";
		private static final long DEFAULT_WAIT_TIME = 10000l;
		static KnowledgeBase kbase;
		StatefulKnowledgeSession ksession;
		TaskClient client ;
        ProcessInstance pi;
        UserTransaction ut;
        EntityManagerFactory emf;
        
	    public void startWorkflow() {
	    	
	       
	    	ksession = getKsession(-1);
	    	System.out.println("ksessionId:"+ksession.getId());
	    	//ShoppingCart sc = new ShoppingCart();
	        //int sessionId = ksession.getId();
	    	
	    	/*if (client == null)
	    		client = getHornetQTaskClientInstance(ksession);	*/    	
	    	//LocalTaskService localTaskService = getTaskService(ksession);   
	    	//client.connect();
	        /*org.drools.runtime.process.WorkItemManager wim = ksession.getWorkItemManager();
			EmailServiceHandler1 emailServiceWIH = new EmailServiceHandler1();
			wim.registerWorkItemHandler("EmailTask", emailServiceWIH);
			wim.registerWorkItemHandler("EntEmail", emailServiceWIH);*/
	        
	        System.out.println("Before setting the params");
	        Map<String, Object> params = new HashMap<String, Object>();
	       // sc = new ShoppingCart();
	        //System.out.println(orderAmt);
	      //  sc.setAmount(orderAmt);
	        params.put("subprocessInd", "here is your indicator");
	       // ksession.insert(sc);
			
	        try {
	    		ut = (UserTransaction) new InitialContext().lookup( "java:comp/UserTransaction" );
				ut.begin();	
				Globals temp = ksession.getGlobals();
				
				System.out.println("globals: " + temp);
			//	ksession.setGlobal("subProcessComplete", new Boolean(false));
				
		        pi = ksession.startProcess(PROCESS_NAME,params);
		        
		        
		        System.out.println("pi"+pi);
		        
		        ksession.fireAllRules();
		      //  System.out.println("Updated amount:" + sc.getAmount());
		       // System.out.println("Updated discount:" + sc.getDiscount());
		        ut.commit();
		        //ksession.dispose();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicMixedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//ksession.dispose();
	      //  return sc;
	        
	    }
	    public void signalevent(int ksessionId,long pid ){
	    	System.out.println("in signal event");
	    	System.out.println("ksessionId:"+ksessionId);
	    	System.out.println("pid:"+pid);
	    	//System.out.println("pi"+pi);
	    	ksession = getKsession(ksessionId);
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	//pi = ksession.startProcess(PROCESS_NAME,params);
	    	System.out.println("after re-starting ksession");
	    	//kbase = getKnowledgeBase();
	    	//ksession = kbase.newStatefulKnowledgeSession();
	    	//pi.signalEvent("mySignal", null);
	    	//this.ksession.signalEvent("mySignal", null, pi.getId());
	    	ksession.signalEvent("mySignal", null, pid);
	    }
	    
	    public List<TaskSummary> retrieveIndTaskList(String actorId, String group) throws Exception { 
	    	//kbase = getKnowledgeBase();
	    	//ksession = kbase.newStatefulKnowledgeSession();
	    	if (ksession == null)
	    		ksession = getKsession(-1);
	    	
	    	//TaskClient client = getJMSTaskClientInstance(ksession);
	    	/*if (client == null)
	    		client = getHornetQTaskClientInstance(ksession);*/
	    	LocalTaskService lts = getTaskService(ksession);
	    	System.out.println("After Task Client creation");
	    	/*List<String> lGroups = new ArrayList<String>();            
	    	lGroups.add(group);
	    	List<Status> lStatus = new ArrayList<Status>();            
	    	lStatus.add(Status.Ready);
	    	System.out.println("lGroups size"+lGroups.size());
	    	BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
	    	System.out.println("Call to get Task for actor:"+actorId);

	    	//client.connect();
	    	client.getTasksOwned(actorId, "en-UK", responseHandler);
	    	

			System.out.println("Before Getting the Listing");
			
			List<TaskSummary> indTasks = responseHandler.getResults();
			*/
			List<TaskSummary> indTasks = lts.getTasksOwned(actorId, "en-UK");
			System.out.println("List"+indTasks.size());

	    	//List<TaskSummary> list = localTaskService.getTasksAssignedAsPotentialOwner(actorId, "en-UK"); 
	    	System.out.println("retrieveTaskList by " + actorId); 
	    	for (TaskSummary task : indTasks) { 
	    		System.out.println(" task.getId() = " + task.getId()+" "+task.getName()+" "+task.getProcessId()+" "+task.getProcessInstanceId()+" "+task.getProcessSessionId()+" "+task.getStatus()); 
	    	} 
	    	//ksession.dispose();
	    	return indTasks; 
	    }
	    public List<TaskSummary> retrieveGroupTaskList(String group) throws Exception { 
	    	//kbase = getKnowledgeBase();
	    	//ksession = kbase.newStatefulKnowledgeSession();
	    	if (ksession == null)
	    		ksession = getKsession(-1);
	    	
	    	//TaskClient client = getJMSTaskClientInstance(ksession);
	    	/*if (client == null)
	    		client = getHornetQTaskClientInstance(ksession);*/
	    	//client.connect();
	    	LocalTaskService lts = getTaskService(ksession);
	    	System.out.println("After Task Client creation");
	    	List<String> lGroups = new ArrayList<String>();            
	    	lGroups.add(group);
	    	List<Status> lStatus = new ArrayList<Status>();            
	    	lStatus.add(Status.Ready);
	    	
	    
	    	/*System.out.println("lGroups size"+lGroups.size());
	    	BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
	    	
	    	client.getTasksAssignedAsPotentialOwnerByStatusByGroup("", lGroups, lStatus, "en-UK", responseHandler);

			System.out.println("Before Getting the Listing");
			List<TaskSummary> groupTasks = responseHandler.getResults();
			System.out.println("List"+groupTasks.size());
            */
	    	List<TaskSummary> groupTasks = lts.getTasksAssignedAsPotentialOwnerByStatusByGroup("", lGroups, lStatus, "en-UK");
	    	for (TaskSummary task : groupTasks) { 
	    		System.out.println(" task.getId() = " + task.getId()+" "+task.getName()+" "+task.getProcessId()+" "+task.getProcessInstanceId()+" "+task.getProcessSessionId()+" "+task.getStatus()); 
	    	} 
	    	//ksession.dispose();
	    	return groupTasks; 
	    }
	    
	    public void claimTask(String actorId, String taskId, String group){
	    	//kbase = getKnowledgeBase();
	    	//ksession = kbase.newStatefulKnowledgeSession();
	    	if (ksession == null)
	    		ksession = getKsession(-1);
	    	if (!(ksession == null))
	    		System.out.println("ksession exists. Before Task Client creation");
	    	else
	    		System.out.println("no ksession");
	    	//TaskClient client = getJMSTaskClientInstance(ksession);
	    	/*if (client == null)
	    	  client = getHornetQTaskClientInstance(ksession);*/
	    	LocalTaskService lts = getTaskService(ksession);
	    	System.out.println("after getting client");
	    	//client.connect();
	    	/*BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
	    	System.out.println("before claim"+taskId);*/
	    	List<String> lGroups = new ArrayList<String>();            
	    	lGroups.add(group);
	    	//client.claim(Long.parseLong(taskId), actorId, lGroups, responseHandler);
	    	lts.claim(Long.parseLong(taskId), actorId, lGroups);
	    	//ksession.dispose();
	    }
	    
	    public void startTask(String actorId, Long taskId){
	    	//kbase = getKnowledgeBase();
	    	//ksession = kbase.newStatefulKnowledgeSession();
	    	if (ksession == null)
	    		ksession = getKsession(-1);
	    	
			
				
	    	
	    	if (!(ksession == null))
	    		System.out.println("ksession exists. Before Task Client creation");
	    	else
	    		System.out.println("no ksession");
	    	//TaskClient client = getJMSTaskClientInstance(ksession);
	    	/*if (client == null)
	    	 client = getHornetQTaskClientInstance(ksession);*/
	    	LocalTaskService lts = getTaskService(ksession);
	    	System.out.println("after getting client");
	    	//client.connect();
	    	/*BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
	    	System.out.println("before viewing"+taskId);
	    	client.start( taskId, actorId, responseHandler );*/
	    	lts.start( taskId, actorId);
	    	//ksession.dispose();
	    	
	  
	    }
	    
	    public void completeTask(String actorId, TaskSummary task, Map data){
	    	//kbase = getKnowledgeBase();
	    	//ksession = kbase.newStatefulKnowledgeSession();
	    	if (ksession == null)
	    		ksession = getKsession(-1);
	    	if (!(ksession == null))
	    		System.out.println("ksession exists. Before Task Client creation");
	    	else
	    		System.out.println("no ksession");
	    	//TaskClient client = getJMSTaskClientInstance(ksession);
	    	/*if (client == null)
	    	  client = getHornetQTaskClientInstance(ksession);*/
	    	LocalTaskService lts = getTaskService(ksession);
	    	System.out.println("after getting client");
	    	//client.connect();
	    	System.out.println("Completing task " + task.getId());
			//BlockingTaskOperationResponseHandler operationResponseHandler = new BlockingTaskOperationResponseHandler();
			ContentData contentData = null;
			if (data != null) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream out;
				try {
					out = new ObjectOutputStream(bos);
					out.writeObject(data);
					out.close();
					contentData = new ContentData();
					contentData.setAccessType(AccessType.Inline);
					contentData.setContent(bos.toByteArray());	
					System.out.println("Content Data created");
				}
				catch (IOException e) {
					System.err.print(e);
				}
			}
			
	    	//client.complete(Long.parseLong(taskId), actorId, data, responseHandler);
			lts.complete(task.getId(), actorId, contentData);
	    	//client.complete(task.getId(), actorId, contentData, operationResponseHandler);
	    	System.out.println("Completed task " + task.getId());
	    	//ksession.dispose();
	    	//assertProcessInstanceCompleted(task.getProcessInstanceId(), ksession);
	    	//System.out.println("after assert");
			//operationResponseHandler.waitTillDone(DEFAULT_WAIT_TIME);
	    	
	    	
	    }
	    private  StatefulKnowledgeSession getKsession(int ksessionId) {
	    	    KnowledgeBase kbase = getKnowledgeBase();
	    	    //ksession = kbase.newStatefulKnowledgeSession();
		        
		        // create the entity manager factory and register it in the environment

		        emf =Persistence.createEntityManagerFactory( "org.jbpm.persistence.jpa" );

		        Environment env = KnowledgeBaseFactory.newEnvironment();

		        env.set( EnvironmentName.ENTITY_MANAGER_FACTORY, emf );
                
		        if (ksessionId == -1){
		        	ksession = JPAKnowledgeService.newStatefulKnowledgeSession( kbase, null, env );
		        	
		        	
		        }else{
		        	ksession = JPAKnowledgeService.loadStatefulKnowledgeSession( ksessionId, kbase, null, env );
		        }
		        LocalTaskService localTaskService = getTaskService(ksession); 
		        org.drools.runtime.process.WorkItemManager wim = ksession.getWorkItemManager();
		        EmailServiceHandler1 emailServiceWIH = new EmailServiceHandler1();
	        	//wim.registerWorkItemHandler("EmailTask", emailServiceWIH);
	        	wim.registerWorkItemHandler("EntEmail", emailServiceWIH);
	    	return ksession;
	    }
	    
	    private KnowledgeBase getKnowledgeBase(){
	    	System.out.println("in processworkflow");
	    	KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent( "MyAgent" );
	    	System.out.println("before setting the ChangeSet");
	    	kagent.applyChangeSet( ResourceFactory.newClassPathResource("ChangeSet.xml"));
		    System.out.println("After setting the ChangeSet");
		    KnowledgeBase kbase = kagent.getKnowledgeBase();
		    System.out.println("kb size"+kbase.getKnowledgePackages().size());
		    return kbase;
		    /*KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	         kbuilder.add(ResourceFactory.newClassPathResource("ChangeSet.xml"), ResourceType.CHANGE_SET);
	         return kbuilder.newKnowledgeBase();*/
		    
	    }
	    
	    private static TaskClient getHornetQTaskClientInstance(StatefulKnowledgeSession ksession) {	
	    	    System.out.println("hornetq in");
    	        TaskClient client = new TaskClient(new HornetQTaskClientConnector("taskClient"+ UUID.randomUUID(),
                new HornetQTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
    	        //AsyncWSHumanTaskHandler handler = new AsyncWSHumanTaskHandler(client, ksession);
    	        //boolean b = client.connect("hcs591jbosda903",1234);
    	        boolean b = client.connect("hcs591jbosda903",1234);
    	        CommandBasedHornetQWSHumanTaskHandler handler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
    	        handler.setClient(client);
    	        handler.setConnection("hcs591jbosda903",1234);
    	        //handler.setConnection("hcs591jbosda903",1234);
    	        ksession.getWorkItemManager().registerWorkItemHandler("Human Task", handler);
    	        System.out.println("hornetq out");
	            return client;
	    }
	    
	    private LocalTaskService getTaskService(StatefulKnowledgeSession ksession) {	
		   
		    	  TaskService taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
	  	      
		    	  LocalTaskService localTaskService = new LocalTaskService(taskService);
		    	 // boolean connection = client.connect("hcs591jbosda903",1234); 
	  	        //System.out.println("connection status: " + connection);
	  	        //CommandBasedHornetQWSHumanTaskHandler handler = new CommandBasedHornetQWSHumanTaskHandler(ksession);
	  	       // AsyncWSHumanTaskHandler handler = new AsyncWSHumanTaskHandler(client, ksession);
	  	     //   handler.setClient(client);
	  	        //handler.setConnection("hcs591jbosda903",1234); 
	  	        //handler.connect();
	  	       // handler.setConnection("hcs591jbosda903.state.mi.us",1234);
		    	  
		    	  SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
		    	  humanTaskHandler.setLocal(true);
		          humanTaskHandler.connect();
		    	  
	  	          ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
	  	         
		          return localTaskService;
		    }
	   
	}
