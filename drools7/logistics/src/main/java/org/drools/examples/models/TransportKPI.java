package org.drools.examples.models;

/**
 * 包含起运地、目的地、项目代码、起运地、目的地、客户 KPI、阀值
 * @author kylin
 *
 */
public class TransportKPI {

	private String START_PLACE;
	
	private String DESTINATION;
	
	private String PROJECT_CODE ;
	
	private String CUSTOMER_KPI;
	
	private String THRESHOLD;

	public String getSTART_PLACE() {
		return START_PLACE;
	}

	public void setSTART_PLACE(String sTART_PLACE) {
		START_PLACE = sTART_PLACE;
	}

	public String getDESTINATION() {
		return DESTINATION;
	}

	public void setDESTINATION(String dESTINATION) {
		DESTINATION = dESTINATION;
	}

	public String getPROJECT_CODE() {
		return PROJECT_CODE;
	}

	public void setPROJECT_CODE(String pROJECT_CODE) {
		PROJECT_CODE = pROJECT_CODE;
	}

	public String getCUSTOMER_KPI() {
		return CUSTOMER_KPI;
	}

	public void setCUSTOMER_KPI(String cUSTOMER_KPI) {
		CUSTOMER_KPI = cUSTOMER_KPI;
	}

	public String getTHRESHOLD() {
		return THRESHOLD;
	}

	public void setTHRESHOLD(String tHRESHOLD) {
		THRESHOLD = tHRESHOLD;
	}
	
}
