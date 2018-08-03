package org.jbpm.test.humantask;

import java.util.Collection;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.command.Command;
import org.drools.event.process.ProcessEventListener;
import org.drools.event.rule.AgendaEventListener;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.Calendars;
import org.drools.runtime.Channel;
import org.drools.runtime.Environment;
import org.drools.runtime.ExitPoint;
import org.drools.runtime.Globals;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.ObjectFilter;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItemManager;
import org.drools.runtime.rule.Agenda;
import org.drools.runtime.rule.AgendaFilter;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.LiveQuery;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.ViewChangedEventListener;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionClock;

public class TestStatefulKnowledgeSession implements StatefulKnowledgeSession {
	
	public static int testSessionId = 5;
	
	private WorkItemManager workItemManager;
	
	public void setWorkItemManager(WorkItemManager workItemManager) {
		this.workItemManager = workItemManager;
	}

	public WorkItemManager getWorkItemManager() {
		return workItemManager;
	}

	public int fireAllRules() {
		return 0;
	}

	public int fireAllRules(int max) {
		return 0;
	}

	public int fireAllRules(AgendaFilter agendaFilter) {
		return 0;
	}

	public int fireAllRules(AgendaFilter agendaFilter, int max) {
		return 0;
	}

	public void fireUntilHalt() {

	}

	public void fireUntilHalt(AgendaFilter agendaFilter) {

	}

	public <T> T execute(Command<T> command) {
		return null;
	}

	public <T extends SessionClock> T getSessionClock() {
		return null;
	}

	public void setGlobal(String identifier, Object value) {

	}

	public Object getGlobal(String identifier) {
		return null;
	}

	public Globals getGlobals() {
		return null;
	}

	public Calendars getCalendars() {
		return null;
	}

	public Environment getEnvironment() {
		return null;
	}

	public KnowledgeBase getKnowledgeBase() {
		return null;
	}

	public void registerExitPoint(String name, ExitPoint exitPoint) {

	}

	public void unregisterExitPoint(String name) {

	}

	public void registerChannel(String name, Channel channel) {

	}

	public void unregisterChannel(String name) {

	}

	public Map<String, Channel> getChannels() {
		return null;
	}

	public KnowledgeSessionConfiguration getSessionConfiguration() {
		return null;
	}

	public void halt() {

	}

	public Agenda getAgenda() {
		return null;
	}

	public WorkingMemoryEntryPoint getWorkingMemoryEntryPoint(String name) {
		return null;
	}

	public Collection<? extends WorkingMemoryEntryPoint> getWorkingMemoryEntryPoints() {
		return null;
	}

	public QueryResults getQueryResults(String query, Object... arguments) {
		return null;
	}

	public LiveQuery openLiveQuery(String query, Object[] arguments, ViewChangedEventListener listener) {
		return null;
	}

	public String getEntryPointId() {
		return null;
	}

	public FactHandle insert(Object object) {
		return null;
	}

	public void retract(FactHandle handle) {

	}

	public void update(FactHandle handle, Object object) {

	}

	public FactHandle getFactHandle(Object object) {
		return null;
	}

	public Object getObject(FactHandle factHandle) {
		return null;
	}

	public Collection<Object> getObjects() {
		return null;
	}

	public Collection<Object> getObjects(ObjectFilter filter) {
		return null;
	}

	public <T extends FactHandle> Collection<T> getFactHandles() {
		return null;
	}

	public <T extends FactHandle> Collection<T> getFactHandles(ObjectFilter filter) {
		return null;
	}

	public long getFactCount() {
		return 0;
	}

	public ProcessInstance startProcess(String processId) {
		return null;
	}

	public ProcessInstance startProcess(String processId, Map<String, Object> parameters) {
		return null;
	}

	public ProcessInstance createProcessInstance(String processId, Map<String, Object> parameters) {
		return null;
	}

	public ProcessInstance startProcessInstance(long processInstanceId) {
		return null;
	}

	public void signalEvent(String type, Object event) {

	}

	public void signalEvent(String type, Object event, long processInstanceId) {

	}

	public Collection<ProcessInstance> getProcessInstances() {
		return null;
	}

	public ProcessInstance getProcessInstance(long processInstanceId) {
		return null;
	}

	public void abortProcessInstance(long processInstanceId) {

	}

	public void addEventListener(WorkingMemoryEventListener listener) {

	}

	public void removeEventListener(WorkingMemoryEventListener listener) {

	}

	public Collection<WorkingMemoryEventListener> getWorkingMemoryEventListeners() {
		return null;
	}

	public void addEventListener(AgendaEventListener listener) {

	}

	public void removeEventListener(AgendaEventListener listener) {

	}

	public Collection<AgendaEventListener> getAgendaEventListeners() {
		return null;
	}

	public void addEventListener(ProcessEventListener listener) {

	}

	public void removeEventListener(ProcessEventListener listener) {

	}

	public Collection<ProcessEventListener> getProcessEventListeners() {
		return null;
	}

	public int getId() {
		return testSessionId;
	}

	public void dispose() {

	}

}
