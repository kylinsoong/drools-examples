package org.drools.expert.stu01;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.io.ResourceFactory;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;

public class LicenseApplication {
	
	public void test() {
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add( ResourceFactory.newClassPathResource( "licenseApplication.drl", getClass() ), ResourceType.DRL );
		if ( kbuilder.hasErrors() ) {
			System.err.println(kbuilder.getErrors().toString());
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        StatelessKnowledgeSession ksession = kbase.newStatelessKnowledgeSession();
        Applicant applicant = new Applicant( "Mr John Smith", 16 );
        ksession.execute(applicant);
        System.out.println(applicant.isValid());
        
        //Batch Executor
        List<Command> cmds = new ArrayList<Command>();
        cmds.add(CommandFactory.newInsert(new Applicant("Tom", 16), "cmd1"));
        cmds.add(CommandFactory.newInsert(new Applicant("Tim", 26), "cmd2"));
        ExecutionResults results = ksession.execute(CommandFactory.newBatchExecution(cmds));
        
        Applicant a1 = (Applicant) results.getValue("cmd1");
    	System.out.println(a1.getName() + ": " + a1.isValid());
    	Applicant a2 = (Applicant) results.getValue("cmd2");
    	System.out.println(a2.getName() + ": " + a2.isValid());
	}

	public static void main(String[] args) {
		
		new LicenseApplication().test();

	}
}
