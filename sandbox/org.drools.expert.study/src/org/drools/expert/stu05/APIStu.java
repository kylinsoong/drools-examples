package org.drools.expert.stu05;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.KnowledgePackage;
import org.drools.expert.stu.StuBase;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;

public class APIStu extends StuBase{

	
	public static void main(String[] args) throws Exception {

		APIStu stu = new APIStu();
		
//		stu.test1();
		
		stu.test2();
	}

	private void test2() throws MalformedURLException {
		
		KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("KylinAgent");
		kagent.applyChangeSet( ResourceFactory.newUrlResource( new File("").toURL() ) );
		KnowledgeBase kbase = kagent.getKnowledgeBase();
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		
		ResourceChangeScannerConfiguration sconf = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		// Set the disk scanning interval to 30s, default is 60s.
		sconf.setProperty( "drools.resource.scanner.interval", "30" );
		ResourceFactory.getResourceChangeScannerService().configure( sconf );


	}

	private void test1() throws Exception {
		
		KnowledgeBuilder kbuilder1 = KnowledgeBuilderFactory.newKnowledgeBuilder();
		
		KnowledgeBuilderConfiguration kbuilderConf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null, Thread.class.getClassLoader());
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder(kbuilderConf);
		kbuilder.add(ResourceFactory.newFileResource("licenseApplication.drl"), ResourceType.DRL);
		if (kbuilder.hasErrors()) {
			System.out.println(kbuilder.getErrors());
			return;
		}

		Collection<KnowledgePackage> kpkgs = kbuilder.getKnowledgePackages();

		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kpkgs);
		
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( "testOutput" ) );
		out.writeObject( kpkgs );
		out.close();

		ObjectInputStream in = new ObjectInputStream( new FileInputStream( "testOutput" ) );
		Collection<KnowledgePackage> kpkgs2 = (Collection<KnowledgePackage>) in.readObject();
		in.close();
		
		kbase.addKnowledgePackages(kpkgs2);
		
		
		pause();
	}

}
