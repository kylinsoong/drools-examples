package org.drools.expert.stu03;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.expert.stu.StuBase;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

public class FireAlarmTest extends StuBase{

	public static void main(String[] args) {
		new FireAlarmTest().test();
	}

	private void test() {
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add( ResourceFactory.newClassPathResource( "fireAlarm.drl", getClass() ), ResourceType.DRL );
		if ( kbuilder.hasErrors() ) {
			System.err.println(kbuilder.getErrors().toString());
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
	
        String[] names = new String[]{"kitchen", "bedroom", "office", "livingroom"};
        Map<String,Room> name2room = new HashMap<String,Room>();
		for (String name : names ){
			Room room = new Room(name);
			name2room.put(name, room);
			ksession.insert(room);
			Sprinkler sprinkler = new Sprinkler( room );
			ksession.insert( sprinkler );
        }
		ksession.fireAllRules();
		
		sleep();

		Fire kitchenFire = new Fire( name2room.get( "kitchen" ) );
		Fire officeFire = new Fire( name2room.get( "office" ) );
		
		FactHandle kitchenFireHandle = ksession.insert( kitchenFire );
		ksession.fireAllRules();
		sleep();
		
		FactHandle officeFireHandle = ksession.insert( officeFire );
		ksession.fireAllRules();
		sleep();
		
		ksession.retract( kitchenFireHandle );
		ksession.fireAllRules();
		sleep();
		
		ksession.retract( officeFireHandle );
		ksession.fireAllRules();
		sleep();
	}

	

}
