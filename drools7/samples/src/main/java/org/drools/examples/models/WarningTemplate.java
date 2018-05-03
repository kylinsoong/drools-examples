package org.drools.examples.models;

/**
 * 
 * 包含预警类型(状态预警、船期预警、运输时效 KPI 预警),
前置状态、前置状态地点、监控状态、监控状态地点、时间间隔、
是否工作日预警、时间类型(船期类型)、推送方式。
 * @author kylin
 *
 */
public class WarningTemplate {

	private WarningType TYPE;
	
	private String PRE_STATUS;
	
	private String PRE_STATUS_PLACE;
	
	private String STATUS;
	
	private String STATUS_PLACE;
	
	private Long TIME_INTERVAL;
	
	private Boolean IS_WORKDAY_WARNING;
	
	private String TIME_TYPE;
	
	private String NOTIFICATION_TYPE;

	public WarningType getTYPE() {
		return TYPE;
	}

	public void setTYPE(WarningType tYPE) {
		TYPE = tYPE;
	}

	public String getPRE_STATUS() {
		return PRE_STATUS;
	}

	public void setPRE_STATUS(String pRE_STATUS) {
		PRE_STATUS = pRE_STATUS;
	}

	public String getPRE_STATUS_PLACE() {
		return PRE_STATUS_PLACE;
	}

	public void setPRE_STATUS_PLACE(String pRE_STATUS_PLACE) {
		PRE_STATUS_PLACE = pRE_STATUS_PLACE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getSTATUS_PLACE() {
		return STATUS_PLACE;
	}

	public void setSTATUS_PLACE(String sTATUS_PLACE) {
		STATUS_PLACE = sTATUS_PLACE;
	}

	public Long getTIME_INTERVAL() {
		return TIME_INTERVAL;
	}

	public void setTIME_INTERVAL(Long tIME_INTERVAL) {
		TIME_INTERVAL = tIME_INTERVAL;
	}

	public Boolean getIS_WORKDAY_WARNING() {
		return IS_WORKDAY_WARNING;
	}

	public void setIS_WORKDAY_WARNING(Boolean iS_WORKDAY_WARNING) {
		IS_WORKDAY_WARNING = iS_WORKDAY_WARNING;
	}

	public String getTIME_TYPE() {
		return TIME_TYPE;
	}

	public void setTIME_TYPE(String tIME_TYPE) {
		TIME_TYPE = tIME_TYPE;
	}

	public String getNOTIFICATION_TYPE() {
		return NOTIFICATION_TYPE;
	}

	public void setNOTIFICATION_TYPE(String nNOTIFICATION_TYPE) {
		NOTIFICATION_TYPE = nNOTIFICATION_TYPE;
	}
	
}
