package org.jbpm.test.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.process.Connection;
import org.drools.definition.process.Node;
import org.drools.definition.process.WorkflowProcess;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.workflow.core.Constraint;
import org.jbpm.workflow.core.impl.ConnectionRef;
import org.jbpm.workflow.core.node.ActionNode;
import org.jbpm.workflow.core.node.Split;

public class BPMNProcessDumperTest {

	public static void main(String[] args) {
		
		StatefulKnowledgeSession ksession = createKSession();
		Map <String,Object> procVar = new HashMap<String,Object>();
		procVar.put("myVar", new Integer("2"));
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello",procVar);
		RuleFlowProcess definition = ((RuleFlowProcess)processInstance.getProcess());
		reproduce(definition);
		
	}
	
	private static void reproduce(WorkflowProcess process) {
		Node[] nodes = process.getNodes();
		List<Connection> connections = new ArrayList<Connection>();
        for (Node node: nodes) {
        	if(node instanceof Split){
        		Split split = (Split) node;
        		for(Constraint cons : split.getConstraints().values()){
        			System.out.println();
        			System.out.println("Constraint: " + cons.getConstraint());
        			System.out.println("Dialect: " + cons.getDialect());
        			System.out.println("Name: " + cons.getName());
        			System.out.println("Priority: " + cons.getPriority());
        			System.out.println("Type: " + cons.getType());
        		}
        		
        	}
        	
        	if(node instanceof ActionNode){
        		for (List<Connection> connectionList: node.getIncomingConnections().values()) {
                    connections.addAll(connectionList);
                }
        	} 
        }
        
        for(Connection connection : connections){
        	System.out.println();
        	System.out.println("From Process definition Constraint should");
        	System.out.println();
        	System.out.println();
        	System.out.println("The ");
        	Split split = (Split) connection.getFrom();
            Constraint constraint = split.getConstraint(connection);
            String constraintString = constraint.getConstraint();
            System.out.println(constraintString);
        }
       
	}

	public static StatefulKnowledgeSession createKSession(){
        //Create the kbuilder
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        //Add simpleProcess.bpmn to kbuilder
        kbuilder.add(new ClassPathResource("process/GatewayEvaluationJava.bpmn"), ResourceType.BPMN2);
        
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
