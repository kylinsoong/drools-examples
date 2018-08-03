package org.drools;

import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieContainerImpl;
import org.drools.compiler.kie.builder.impl.KieProject;
import org.drools.compiler.kie.builder.impl.ResultsImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;

public class KieMavenPluginTest {

	public static void main(String[] args) throws MojoFailureException {

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		
		KieServices ks = KieServices.Factory.get();
		
		try {
            KieRepository kr = ks.getRepository();
            KieModule kModule = kr.addKieModule(ks.getResources().newFileSystemResource("src/main/resources"));
            KieContainerImpl kContainer = (KieContainerImpl) ks.newKieContainer(kModule.getReleaseId());

            KieProject kieProject = kContainer.getKieProject();
            InternalKieModule includeModule = kieProject.getKieModuleForKBase("kbase0");
            System.out.println(includeModule);
            ResultsImpl messages = kieProject.verify();

            List<Message> errors = messages.filterMessages(Message.Level.ERROR);
            if (!errors.isEmpty()) {
                for (Message error : errors) {
                    System.out.println(error);
                }
                throw new MojoFailureException("Build failed!");
            } 
		} finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

}
