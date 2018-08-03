package org.drools.examples.ksession.test;

public class ParametrosMotor {

	
	private String scanInterval = "30"; // Valor por defecto en segundos
	private String agentNewInstance = "false"; // Valor por defecto
	private String cancelActivation = "false";
	private Integer WindowLenght = 40;
	private boolean eventosDebug = false;

	public Integer getWindowLenght() {
		return WindowLenght;
	}

	public void setWindowLenght(Integer windowLenght) {
		WindowLenght = windowLenght;
	}

	private String uriServicioInicializador = "http://localhost:8080/insygnia-entornoReglas/servicioInstanciado.drl"; //Valor por defecto
	private String uriGeneradorReglas = "hsttp://localhost:8080/insygnia-entornoReglas/drl/reglas"; //Valor por defecto
	private String classpathConfiguracionDRL= "classpath:es/cyii/insygnia/entornoReglas/negocio/configuracion.drl"; //Valor por defecto
	private boolean actualizacionReglas=true;
	
	private String classpathConfiguracionDRL1= "classpath:svn/test-1.drl"; 
	private String classpathConfiguracionDRL2= "classpath:svn/test-2.drl"; 
	

	public byte[] getChangeSet(){
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		sb.append("<change-set xmlns='http://drools.org/drools-5.0/change-set' ");
		sb.append("	xmlns:xs='http://www.w3.org/2001/XMLSchema-instance' ");
		sb.append("	xs:schemaLocation='http://drools.org/drools-5.0/change-set.xsd http://anonsvn.jboss.org/repos/labs/labs/jbossrules/trunk/drools-api/src/main/resources/change-set-1.0.0.xsd'> ");
		sb.append("	<add> ");
		sb.append("		<resource source='").append(classpathConfiguracionDRL1).append("' type='DRL' /> ");
		sb.append("		<resource source='").append(classpathConfiguracionDRL2).append("' type='DRL' /> ");
		sb.append("	</add> ");
		sb.append("</change-set>");
		

		return sb.toString().getBytes();
	}
	
	/**
	 * @return the scanInterval
	 */
	public String getScanInterval() {
		return scanInterval;
	}

	/**
	 * @param scanInterval the scanInterval to set
	 */
	public void setScanInterval(String scanInterval) {
		this.scanInterval = scanInterval;
	}

	/**
	 * @return the agentNewInstance
	 */
	public String getAgentNewInstance() {
		return agentNewInstance;
	}

	
	/**
	 * @param agentNewInstance the agentNewInstance to set
	 */
	public void setAgentNewInstance(String agentNewInstance) {
		this.agentNewInstance = agentNewInstance;
	}

	/**
	 * @return the uriServicioInicializador
	 */
	public String getUriServicioInicializador() {
		return uriServicioInicializador;
	}

	/**
	 * @param uriServicioInicializador the uriServicioInicializador to set
	 */
	public void setUriServicioInicializador(String uriServicioInicializador) {
		this.uriServicioInicializador = uriServicioInicializador;
	}
	
	

	public void setUriGeneradorReglas(String uriGeneradorReglas) {
		this.uriGeneradorReglas = uriGeneradorReglas;
	}

	public void setClasspathConfiguracionDRL(String classpathConfiguracionDRL) {
		this.classpathConfiguracionDRL = classpathConfiguracionDRL;
	}

	public String getCancelActivation() {
		return cancelActivation;
	}

	public void setCancelActivation(String cancelActivation) {
		this.cancelActivation = cancelActivation;
	}

	public boolean isEventosDebug() {
		return eventosDebug;
	}

	public void setEventosDebug(boolean eventosDebug) {
		this.eventosDebug = eventosDebug;
	}

	public boolean getActualizacionReglas() {
			
		return this.actualizacionReglas;
	}

	public void setActualizacionReglas(boolean actualizacionReglas) {
		this.actualizacionReglas = actualizacionReglas;
	}
}
