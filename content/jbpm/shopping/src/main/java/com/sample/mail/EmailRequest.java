package com.sample.mail;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmailRequest", propOrder = { "applicationName",
		"applicationURL", "from", "subject", "text", "sendOnPartialSuccess",
		"samRole", "toSAMUserNums", "ccSAMUserNums", "toSAMUserIds",
		"ccSAMUserIds", "toMAPUserIds", "ccMAPUserIds", "toEmailIds",
		"ccEmailIds", "attachmentNameandBytes", "sendAsHtml", "sendWithFooter",
		"htmlImages" })
public class EmailRequest {
	protected String applicationName;
	protected String applicationURL;
	protected String from;

	@XmlElement(required = true)
	protected String subject;

	@XmlElement(required = true)
	protected String text;
	protected boolean sendOnPartialSuccess;

	@XmlElement(name = "SAMRole")
	protected SAMRole samRole;

	@XmlElement(type = Integer.class)
	protected List<Integer> toSAMUserNums;

	@XmlElement(type = Integer.class)
	protected List<Integer> ccSAMUserNums;
	protected List<String> toSAMUserIds;
	protected List<String> ccSAMUserIds;

	@XmlElement(type = Integer.class)
	protected List<Integer> toMAPUserIds;

	@XmlElement(type = Integer.class)
	protected List<Integer> ccMAPUserIds;
	protected List<String> toEmailIds;
	protected List<String> ccEmailIds;

	@XmlElement(name = "AttachmentNameandBytes")
	protected List<AttachmentNameandBytes> attachmentNameandBytes;
	protected boolean sendAsHtml;

	@XmlElement(defaultValue = "true")
	protected boolean sendWithFooter;

	@XmlElement(name = "HtmlImages")
	protected List<HtmlImages> htmlImages;

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String value) {
		this.applicationName = value;
	}

	public String getApplicationURL() {
		return this.applicationURL;
	}

	public void setApplicationURL(String value) {
		this.applicationURL = value;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String value) {
		this.from = value;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String value) {
		this.subject = value;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String value) {
		this.text = value;
	}

	public boolean isSendOnPartialSuccess() {
		return this.sendOnPartialSuccess;
	}

	public void setSendOnPartialSuccess(boolean value) {
		this.sendOnPartialSuccess = value;
	}

	public SAMRole getSAMRole() {
		return this.samRole;
	}

	public void setSAMRole(SAMRole value) {
		this.samRole = value;
	}

	public List<Integer> getToSAMUserNums() {
		if (this.toSAMUserNums == null) {
			this.toSAMUserNums = new ArrayList();
		}
		return this.toSAMUserNums;
	}

	public List<Integer> getCcSAMUserNums() {
		if (this.ccSAMUserNums == null) {
			this.ccSAMUserNums = new ArrayList();
		}
		return this.ccSAMUserNums;
	}

	public List<String> getToSAMUserIds() {
		if (this.toSAMUserIds == null) {
			this.toSAMUserIds = new ArrayList();
		}
		return this.toSAMUserIds;
	}

	public List<String> getCcSAMUserIds() {
		if (this.ccSAMUserIds == null) {
			this.ccSAMUserIds = new ArrayList();
		}
		return this.ccSAMUserIds;
	}

	public List<Integer> getToMAPUserIds() {
		if (this.toMAPUserIds == null) {
			this.toMAPUserIds = new ArrayList();
		}
		return this.toMAPUserIds;
	}

	public List<Integer> getCcMAPUserIds() {
		if (this.ccMAPUserIds == null) {
			this.ccMAPUserIds = new ArrayList();
		}
		return this.ccMAPUserIds;
	}

	public List<String> getToEmailIds() {
		if (this.toEmailIds == null) {
			this.toEmailIds = new ArrayList();
		}
		return this.toEmailIds;
	}

	public List<String> getCcEmailIds() {
		if (this.ccEmailIds == null) {
			this.ccEmailIds = new ArrayList();
		}
		return this.ccEmailIds;
	}

	public List<AttachmentNameandBytes> getAttachmentNameandBytes() {
		if (this.attachmentNameandBytes == null) {
			this.attachmentNameandBytes = new ArrayList();
		}
		return this.attachmentNameandBytes;
	}

	public boolean isSendAsHtml() {
		return this.sendAsHtml;
	}

	public void setSendAsHtml(boolean value) {
		this.sendAsHtml = value;
	}

	public boolean isSendWithFooter() {
		return this.sendWithFooter;
	}

	public void setSendWithFooter(boolean value) {
		this.sendWithFooter = value;
	}

	public List<HtmlImages> getHtmlImages() {
		if (this.htmlImages == null) {
			this.htmlImages = new ArrayList();
		}
		return this.htmlImages;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "appName", "toRoleCodes", "ccRoleCodes" })
	public static class SAMRole {

		@XmlElement(required = true)
		protected String appName;
		protected List<String> toRoleCodes;
		protected List<String> ccRoleCodes;

		public String getAppName() {
			return this.appName;
		}

		public void setAppName(String value) {
			this.appName = value;
		}

		public List<String> getToRoleCodes() {
			if (this.toRoleCodes == null) {
				this.toRoleCodes = new ArrayList();
			}
			return this.toRoleCodes;
		}

		public List<String> getCcRoleCodes() {
			if (this.ccRoleCodes == null) {
				this.ccRoleCodes = new ArrayList();
			}
			return this.ccRoleCodes;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "htmlImageIdentifier", "htmlImageData" })
	public static class HtmlImages {

		@XmlElement(required = true)
		protected String htmlImageIdentifier;

		@XmlElement(required = true)
		protected byte[] htmlImageData;

		public String getHtmlImageIdentifier() {
			return this.htmlImageIdentifier;
		}

		public void setHtmlImageIdentifier(String value) {
			this.htmlImageIdentifier = value;
		}

		public byte[] getHtmlImageData() {
			return this.htmlImageData;
		}

		public void setHtmlImageData(byte[] value) {
			this.htmlImageData = ((byte[]) value);
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "attachmentName", "attachmentData" })
	public static class AttachmentNameandBytes {

		@XmlElement(required = true)
		protected String attachmentName;

		@XmlElement(required = true)
		protected byte[] attachmentData;

		public String getAttachmentName() {
			return this.attachmentName;
		}

		public void setAttachmentName(String value) {
			this.attachmentName = value;
		}

		public byte[] getAttachmentData() {
			return this.attachmentData;
		}

		public void setAttachmentData(byte[] value) {
			this.attachmentData = ((byte[]) value);
		}
	}
}
