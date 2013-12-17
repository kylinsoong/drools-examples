package org.jbpm.demo.approval.ejb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;
import org.kie.internal.runtime.manager.context.EmptyContext;

@Startup
@javax.ejb.Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessService implements ProcessServiceLocal{
	
	private static final Logger logger = Logger.getLogger(ProcessService.class);
	
	@Resource
    private UserTransaction ut;
	
	@Inject
    @Singleton
    private RuntimeManager singletonManager;
	
	@PostConstruct
    public void configure() {
        // use toString to make sure CDI initializes the bean
        // this makes sure that RuntimeManager is started asap,
        // otherwise after server restart complete task won't move process forward 
        singletonManager.toString();
    }
	
	public long startProcess(String recipient, String ticketname) throws Exception {
		
		RuntimeEngine runtime = singletonManager.getRuntimeEngine(EmptyContext.get());
		
		KieSession ksession = runtime.getKieSession();
		
		long processInstanceId = -1;

        ut.begin();
        
        try {
            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("recipient", recipient);
            params.put("ticketname", ticketname);
            ProcessInstance processInstance = ksession.startProcess("org.jbpm.demo.approval", params);

            processInstanceId = processInstance.getId();

            logger.info("Process started ... : processInstanceId = " + processInstanceId);

            ut.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (ut.getStatus() == Status.STATUS_ACTIVE) {
                ut.rollback();
            }
            throw e;
        } finally {
            ksession.dispose();
        }

        return processInstanceId;
	}
	
}
