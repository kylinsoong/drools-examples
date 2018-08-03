package com.sample;

import gov.michigan.mdot.email.EmailRequest;
import gov.michigan.mdot.email.EmailResponse;
import gov.michigan.mdot.email.service.EmailWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;


public class EmailServiceHandler1 implements WorkItemHandler{
	
	private static final String ENDPOINT_ADDRESS_PARAM = "endpointAddress";
	
	private static final String FROM_EMAIL = "FromEmail";
	
	private static final String SUBJECT = "Subject";
	
	private static final String TEXT = "Text";
	
	private static final String TO_EMAIL = "ToEmail";
	
	private static final String SEND_AS_HTML = "SendAsHtml";
	
	private static final String SEND_ON_PARTIAL_SUCCESS = "SendOnPartialSuccess";
	
	
	private static final String RESPONSE_PARAM = "Result";

  public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
    // extract parameters
	  /*
		 * First retrieve the parameter values passed in by the jBPM5 process. This will include the endpoint location of the PaymentService
		 * and the request parameter (as a String);
		 */
		Map<String, Object> parameters = workItem.getParameters();

		String endpointLocation = (String) parameters.get(ENDPOINT_ADDRESS_PARAM);
		String fromEmail = (String) parameters.get(FROM_EMAIL);
		String subject = (String) parameters.get(SUBJECT);
		String text = (String) parameters.get(TEXT);
		String toEmail = (String) parameters.get(TO_EMAIL);
		Boolean sendAsHtml = Boolean.parseBoolean(String.valueOf(SEND_AS_HTML)); 
		Boolean sendOnPartialSuccess = Boolean.parseBoolean(String.valueOf(SEND_ON_PARTIAL_SUCCESS)); 
        //System.out.println("endpointLocation-"+endpointLocation);
   
        
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setFrom(fromEmail);
        emailRequest.setSendAsHtml(sendAsHtml);
        emailRequest.setSendOnPartialSuccess(sendOnPartialSuccess);
        emailRequest.setSubject(subject);
        emailRequest.setText(text);
        
        ArrayList toEmailIds = new ArrayList();
       
 
		
		
		StringTokenizer st = new StringTokenizer(toEmail, ";");
        
		while (st.hasMoreElements()) {	
			//System.out.println(st.nextElement());
			toEmailIds.add(st.nextElement());
		}
       
        emailRequest.getToEmailIds().addAll(toEmailIds);
        
        EmailWebService emailWebService = new EmailWebService();
        

        Map<String, Object> responses = new HashMap<String, Object>();
        
		try {
			EmailResponse emailResponse = emailWebService
					.sendMDOTEmail(emailRequest);
			responses.put(RESPONSE_PARAM, emailResponse);
			System.out.println("***********************");
			System.out.println("Return Message:"
					+ emailResponse.getReturnMessage());
			System.out.println("Return Code:" + emailResponse.getReturnCode());
			System.out.println("Return Success:" + emailResponse.isSuccess());
			
			System.out.println(emailResponse.getReturnCode() + "-"
					+ emailResponse.getReturnMessage());
		} catch (Exception e) {
			// TODO: handle exception
		}
		// And complete the WorkItem
		workItemManager.completeWorkItem(workItem.getId(), responses);
  }
 

  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    // Do nothing, notifications cannot be aborted
  }
  
}

