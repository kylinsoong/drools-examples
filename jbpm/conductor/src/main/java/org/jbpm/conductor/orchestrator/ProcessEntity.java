package org.jbpm.conductor.orchestrator;

import java.util.HashMap;
import java.util.Map;


public class ProcessEntity {
	
	public ProcessEntity() {
		
	}
	
	public ProcessEntity(String processStartId, Map<String, Object> processParamMap) {
		super();
		this.processStartId = processStartId;
		this.processParamMap = processParamMap;
	}

	private long processInstanceId;
	
	private String processStartId = null;
	
	private Map<String, Object> processParamMap = new HashMap<String, Object> ();

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessStartId() {
		return processStartId;
	}

	public void setProcessStartId(String processStartId) {
		this.processStartId = processStartId;
	}

	public Map<String, Object> getProcessParamMap() {
		return processParamMap;
	}

	public void setProcessParamMap(Map<String, Object> processParamMap) {
		this.processParamMap = processParamMap;
	}

}
