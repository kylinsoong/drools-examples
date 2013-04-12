package com.sample.mail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailResponse", namespace = "http://www.michigan.gov/mdot/enterprise/EmailResponse", propOrder = {"returnCode", "returnMessage", "success" })
public class EmailResponse {
	protected int returnCode;

	@XmlElement(required = true)
	protected String returnMessage;
	protected boolean success;

	public int getReturnCode() {
		return this.returnCode;
	}

	public void setReturnCode(int value) {
		this.returnCode = value;
	}

	public String getReturnMessage() {
		return this.returnMessage;
	}

	public void setReturnMessage(String value) {
		this.returnMessage = value;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean value) {
		this.success = value;
	}
}