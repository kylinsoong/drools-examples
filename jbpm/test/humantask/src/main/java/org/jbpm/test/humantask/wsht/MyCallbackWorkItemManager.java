package org.jbpm.test.humantask.wsht;

import java.util.Map;

import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class MyCallbackWorkItemManager implements WorkItemManager {
	
	private volatile boolean completed;
    private volatile boolean aborted;
    private volatile boolean error;
    private volatile Map<String, Object> results;
    
    public synchronized boolean waitTillCompleted(long time) {
        if (!isCompleted()) {
            try {
                wait(time);
            } catch (InterruptedException e) {
                // swallow and return state of completed
            }
        }

        return isCompleted();
    }
    
    private synchronized void setCompleted(boolean completed) {
        this.completed = completed;
        notifyAll();
    }
    
    public synchronized boolean isCompleted() {
        return completed;
    }
    
    public synchronized boolean waitTillAborted(long time) {
        if (!isAborted()) {
            try {
                wait(time);
            } catch (InterruptedException e) {
                // swallow and return state of aborted
            }
        }

        return isAborted();
    }
    
    public synchronized boolean isAborted() {
        return aborted;
    }

    private synchronized void setAborted(boolean aborted) {
        this.aborted = aborted;
        notifyAll();
    }

	public void completeWorkItem(long id, Map<String, Object> results) {
		
		if (this.aborted || this.completed) {
            this.error = true;
            return;
        }
        this.results = results;
        setCompleted(true);
		
	}

	public void abortWorkItem(long id) {
		
		if (this.aborted || this.completed) {
            this.error = true;
            return;
        }
        setAborted(true);
		
	}
	
	public Map<String, Object> getResults() {
        return results;
    }

	public void registerWorkItemHandler(String workItemName, WorkItemHandler handler) {
		
	}

	 public boolean isError() {
         return error;
     }

     public void setError(boolean error) {
         this.error = error;
     }

}
