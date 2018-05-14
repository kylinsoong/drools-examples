/*
 * JBoss, Home of Professional Open Source
 * Copyright 2017, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sample.model;

import java.math.BigInteger;

/**
 * @author rafaelbenevides
 * 
 */
public class Transaction {

    private BigInteger value = new BigInteger("10");

    private boolean denied;

    private String deniedCause;

    private TransactionType type;

    public Transaction() {

    }

    /**
     * @param value
     * @param type
     */
    public Transaction(BigInteger value, TransactionType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * @return the value
     */
    public BigInteger getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigInteger value) {
        this.value = value;
    }

    /**
     * @return the denied
     */
    public boolean isDenied() {
        return denied;
    }

    /**
     * @param denied the denied to set
     */
    public void setDenied(boolean denied) {
        this.denied = denied;
    }

    /**
     * @return the deniedCause
     */
    public String getDeniedCause() {
        return deniedCause;
    }

    /**
     * @param deniedCause the deniedCause to set
     */
    public void setDeniedCause(String deniedCause) {
        this.deniedCause = deniedCause;
    }

    /**
     * @return the type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

	@Override
	public String toString() {
		return "Transaction [value=" + value + ", type=" + type + "]";
	}

}
