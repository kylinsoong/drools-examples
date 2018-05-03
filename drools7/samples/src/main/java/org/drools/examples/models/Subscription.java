package org.drools.examples.models;

/**
 * 包括系统、项目、客户、公司。系统是唯一必输入项，此外可以再选填项目，客户，公司信息，三个里面只能选1个。
 * @author kylin
 *
 */
public class Subscription {
	
	/*
	 * 系统, 唯一必输入项
	 */
	private String STATUS_SECURCE;
	
	/*
	 * 项目
	 */
	private String PROJECT_CODE ;
	
	/*
	 * 客户
	 */
	private String STANDARD_CUS_ID;
	
	/*
	 * 公司
	 */
	private String STANDARD_ORG;

	public String getSTATUS_SECURCE() {
		return STATUS_SECURCE;
	}

	public void setSTATUS_SECURCE(String sTATUS_SECURCE) {
		STATUS_SECURCE = sTATUS_SECURCE;
	}

	public String getPROJECT_CODE() {
		return PROJECT_CODE;
	}

	public void setPROJECT_CODE(String pROJECT_CODE) {
		PROJECT_CODE = pROJECT_CODE;
	}

	public String getSTANDARD_CUS_ID() {
		return STANDARD_CUS_ID;
	}

	public void setSTANDARD_CUS_ID(String sTANDARD_CUS_ID) {
		STANDARD_CUS_ID = sTANDARD_CUS_ID;
	}

	public String getSTANDARD_ORG() {
		return STANDARD_ORG;
	}

	public void setSTANDARD_ORG(String sTANDARD_ORG) {
		STANDARD_ORG = sTANDARD_ORG;
	}

}
