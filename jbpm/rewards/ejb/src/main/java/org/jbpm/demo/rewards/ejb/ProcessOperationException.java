package org.jbpm.demo.rewards.ejb;

/**
 * 
 * Used for recoverable exception like OptimisticLockException
 *
 */
public class ProcessOperationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProcessOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
