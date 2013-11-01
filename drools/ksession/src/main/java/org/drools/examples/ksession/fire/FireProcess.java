package org.drools.examples.ksession.fire;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class FireProcess {

	public static void main(String[] args) throws Exception {
		KnowledgeBase kbase = readKnowledgeBase("test-1.drl");
//		KnowledgeBase kbase = readKnowledgeBase("test-2.drl");
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        ksession.insert("Hello");
        ksession.insert("There");
        ksession.insert("!");
        ksession.fireAllRules();
        ksession.dispose();
	}
	
	private static KnowledgeBase readKnowledgeBase(String name) throws Exception {
		KnowledgeBuilderConfiguration kconf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		kconf.setProperty("drools.dump.dir", "/home/kylin/work/tmp/974943");
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kconf);
        kbuilder.add(ResourceFactory.newClassPathResource(name), ResourceType.DRL);
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

}
