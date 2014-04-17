package org.drools.examples.ksession.test;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.agent.impl.PrintStreamSystemEventListener;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.conf.EventProcessingOption;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class MotorReglas {
	
	private StatefulKnowledgeSession ksession=null;
	private KnowledgeAgent kagent;
	private KnowledgeBase kbase;
	private KnowledgeBaseConfiguration kconfig;
	private KnowledgeAgentConfiguration kaconf;
	
	ParametrosMotor parametrosMotor = new ParametrosMotor();
	
	public MotorReglas() {
		
		KnowledgeBuilderConfiguration kbc = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
		
		kconfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		kconfig.setOption(EventProcessingOption.STREAM);
		
		kbase = KnowledgeBaseFactory.newKnowledgeBase(kconfig);
		kbase.addEventListener(new KnowledgeBaseEventListenerImpl());
		
		ResourceChangeScannerConfiguration sconf = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
		sconf.setProperty("drools.resource.scanner.interval",this.parametrosMotor.getScanInterval());
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		ResourceFactory.getResourceChangeNotifierService().start();
		ResourceFactory.getResourceChangeScannerService().start();
		
		this.kaconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		this.kaconf.setProperty("drools.agent.scanDirectories", "true");
		this.kaconf.setProperty("drools.agent.scanResources", "true");
		this.kaconf.setProperty("drools.agent.newInstance", this.parametrosMotor.getAgentNewInstance());
		
		this.kagent = KnowledgeAgentFactory.newKnowledgeAgent("test agent", kbase, kaconf, kbc);
		this.kagent.applyChangeSet(ResourceFactory.newByteArrayResource(this.parametrosMotor.getChangeSet()));
		
		this.ksession = kagent.getKnowledgeBase().newStatefulKnowledgeSession();

		this.kagent.setSystemEventListener(new PrintStreamSystemEventListener());
		this.kagent.addEventListener(new KnowledgeAgentEventListenerImpl());
		this.kagent.monitorResourceChangeEvents(true);
		
	}

	public StatefulKnowledgeSession getKsession() {
		return kagent.getKnowledgeBase().newStatefulKnowledgeSession();
	}

	public void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public KnowledgeAgent getKagent() {
		return kagent;
	}

	public void setKagent(KnowledgeAgent kagent) {
		this.kagent = kagent;
	}

	public KnowledgeBase getKbase() {
		return kbase;
	}

	public void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}

	public KnowledgeBaseConfiguration getKconfig() {
		return kconfig;
	}

	public void setKconfig(KnowledgeBaseConfiguration kconfig) {
		this.kconfig = kconfig;
	}

	public KnowledgeAgentConfiguration getKaconf() {
		return kaconf;
	}

	public void setKaconf(KnowledgeAgentConfiguration kaconf) {
		this.kaconf = kaconf;
	}

	public ParametrosMotor getParametrosMotor() {
		return parametrosMotor;
	}

	public void setParametrosMotor(ParametrosMotor parametrosMotor) {
		this.parametrosMotor = parametrosMotor;
	}

}
