package org.drools;

import java.io.File;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.builder.JaxbConfiguration;
import org.kie.internal.builder.KnowledgeBuilderFactory;

import com.sun.tools.xjc.Language;
import com.sun.tools.xjc.Options;

public class SupportCase01144843 {

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kBase = kContainer.getKieBase("kbase");
		
		Options xjcOpts = new Options();
		xjcOpts.setSchemaLanguage(Language.XMLSCHEMA);
		JaxbConfiguration jaxbConfiguration = KnowledgeBuilderFactory.newJaxbConfiguration( xjcOpts, "xsd" );
		
		KieBuilder kbuilder = ks.newKieBuilder(new File(""));

	}

}
