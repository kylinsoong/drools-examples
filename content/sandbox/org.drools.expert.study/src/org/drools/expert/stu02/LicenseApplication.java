package org.drools.expert.stu02;

import java.util.Date;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.CommandFactory;
import org.drools.common.DefaultFactHandle;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

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
        Application application = new Application();
        application.setDateApplied(new Date());
        
        FactHandle fact = ksession.execute( CommandFactory.newInsert( new Object[] {applicant, application } ) );
        
        System.out.println(fact.toString());
        
        
	}

	public static void main(String[] args) {
		
		new LicenseApplication().test();

	}
}
