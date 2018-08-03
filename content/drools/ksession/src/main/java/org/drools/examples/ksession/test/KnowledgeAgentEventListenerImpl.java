package org.drools.examples.ksession.test;

import org.drools.event.knowledgeagent.AfterChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.AfterChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.AfterResourceProcessedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetAppliedEvent;
import org.drools.event.knowledgeagent.BeforeChangeSetProcessedEvent;
import org.drools.event.knowledgeagent.BeforeResourceProcessedEvent;
import org.drools.event.knowledgeagent.KnowledgeAgentEventListener;
import org.drools.event.knowledgeagent.KnowledgeBaseUpdatedEvent;
import org.drools.event.knowledgeagent.ResourceCompilationFailedEvent;

public class KnowledgeAgentEventListenerImpl implements KnowledgeAgentEventListener {


	public void beforeChangeSetApplied(BeforeChangeSetAppliedEvent event) {
		System.out.println("*** beforeChangeSetApplied: " + event);
	}

	public void afterChangeSetApplied(AfterChangeSetAppliedEvent event) {
		System.out.println("*** afterChangeSetApplied: " + event);
		System.out.println(event.getSource());
	}

	public void beforeChangeSetProcessed(BeforeChangeSetProcessedEvent event) {
		System.out.println("*** beforeChangeSetProcessed: " + event);
	}

	public void afterChangeSetProcessed(AfterChangeSetProcessedEvent event) {
		System.out.println("*** afterChangeSetProcessed: " + event);
	}

	public void beforeResourceProcessed(BeforeResourceProcessedEvent event) {
		System.out.println("*** beforeResourceProcessed: " + event);
	}

	public void afterResourceProcessed(AfterResourceProcessedEvent event) {
		System.out.println("*** afterResourceProcessed: " + event);
	}

	public void knowledgeBaseUpdated(KnowledgeBaseUpdatedEvent event) {
		System.out.println("knowledgeBaseUpdated: " + event);
	}

	public void resourceCompilationFailed(ResourceCompilationFailedEvent event) {
		System.out.println("resourceCompilationFailed: " + event);
	}
}
