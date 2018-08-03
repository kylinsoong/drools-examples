package com.sample.mail;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
	private static final QName _EmailInput_QNAME = new QName("http://www.michigan.gov/mdot/enterprise/EmailService","EmailInput");
	private static final QName _EmailOutput_QNAME = new QName("http://www.michigan.gov/mdot/enterprise/EmailService","EmailOutput");

	public EmailRequest createEmailRequest() {
		return new EmailRequest();
	}

	public EmailRequest.HtmlImages createEmailRequestHtmlImages() {
		return new EmailRequest.HtmlImages();
	}

	public EmailResponse createEmailResponse() {
		return new EmailResponse();
	}

	public EmailRequest.AttachmentNameandBytes createEmailRequestAttachmentNameandBytes() {
		return new EmailRequest.AttachmentNameandBytes();
	}

	public EmailRequest.SAMRole createEmailRequestSAMRole() {
		return new EmailRequest.SAMRole();
	}

	@XmlElementDecl(namespace = "http://www.michigan.gov/mdot/enterprise/EmailService", name = "EmailInput")
	public JAXBElement<EmailRequest> createEmailInput(EmailRequest value) {
		return new JAXBElement(_EmailInput_QNAME, EmailRequest.class, null, value);
	}

	@XmlElementDecl(namespace = "http://www.michigan.gov/mdot/enterprise/EmailService", name = "EmailOutput")
	public JAXBElement<EmailResponse> createEmailOutput(EmailResponse value) {
		return new JAXBElement(_EmailOutput_QNAME, EmailResponse.class, null,value);
	}
}
