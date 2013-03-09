package com.sample.attributes;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.sample.DroolsTest.Message;


public class AttributesTest {

	public static void main(String[] args) {

		try {
			doGeneralExample();
			doNoLoopExample();
			doLockOnActiveExample();
			doDialectExample();
			doDeclareExample();
			doFromExample();
			doMemberOfExample();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			System.out.println("*****\ndone");
		}
	}

	private static void doMemberOfExample() throws Exception {
		List<Object> facts = new ArrayList<Object>();
		// create list of message
		List<Message> messages = new ArrayList<Message>();
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
	    messages.add(message);
		message = new Message();
		message.setMessage("Hello World2");
		message.setStatus(Message.HELLO);
		messages.add(message);
		facts.add(messages);
		// insert message which is member of list
		facts.add(messages.get(0));
		// insert message which is not member of list
		message = new Message();
		message.setMessage("Hello World3");
		message.setStatus(Message.HELLO);
		facts.add(messages);
		fire("MemberOf.drl", facts);
	}

	private static void doFromExample() throws Exception {
		List<Object> facts = new ArrayList<Object>();
		// create list of message
		List<Message> messages = new ArrayList<Message>();
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
	    messages.add(message);
		message = new Message();
		message.setMessage("Goodbye World2");
		message.setStatus(Message.GOODBYE);
		messages.add(message);
		facts.add(messages);
		fire("From.drl", facts);
	}

	private static void doDeclareExample() throws Exception {

		List<Object> facts = new ArrayList<Object>();    	
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
		facts.add(message);
		fire("Declare.drl", facts);
	}

	private static void doDialectExample() throws Exception {

		List<Object> facts = new ArrayList<Object>();    	
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
		facts.add(message);
		fire("Dialect.drl", facts);  
	}

	private static void doLockOnActiveExample() throws Exception {
		List<Object> facts = new ArrayList<Object>();    	
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
		facts.add(message);    	
		fire("LockOnActive.drl", facts);
	}

	private static void doNoLoopExample() throws Exception {
		List<Object> facts = new ArrayList<Object>();    	
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
		facts.add(message);
    	fire("NoLoop.drl", facts); 
	}

	private static void doGeneralExample() throws Exception {

		List<Object> facts = new ArrayList<Object>();    	
	    Message message = new Message();
	    message.setMessage("Hello World");
	    message.setStatus(Message.HELLO);
		facts.add(message);
		fire("Sample.drl", facts);
	}
	
	private static void fire(String drl, List<Object> facts) throws Exception {
    	System.out.println("*****\n* Example: "+drl+"\n*****");
		KnowledgeBase kbase = readKnowledgeBase(drl);
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
       // go !
       
		for (Object fact : facts) {
			ksession.insert(fact);
		}

		ksession.fireAllRules();
		logger.close();
		ksession.dispose();
	}
	
	private static KnowledgeBase readKnowledgeBase(String drl) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(drl), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
    }

}
