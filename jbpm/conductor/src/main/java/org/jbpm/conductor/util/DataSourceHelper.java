package org.jbpm.conductor.util;

import java.util.Properties;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class DataSourceHelper {
	
	public static DataSourceHelper instance = null;
	
	private DataSourceHelper() {
		
	}
	
	public static DataSourceHelper newInstance(){
		if(instance == null){
			instance = new DataSourceHelper();
		}
		return instance;
	}
	
	private PoolingDataSource pds = null;
	
	public PoolingDataSource setupDataSource(Properties properties) {
		
		if (null != pds) {
			return pds;
		}
		
		System.out.println("Set up PoolingDataSource via " + properties.getProperty("persistence.datasource.url"));
		
        // create data source
		PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName(properties.getProperty("persistence.datasource.name", "jdbc/jbpm-ds"));
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(10);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", properties.getProperty("persistence.datasource.user", "sa"));
        pds.getDriverProperties().put("password", properties.getProperty("persistence.datasource.password", ""));
        pds.getDriverProperties().put("url", properties.getProperty("persistence.datasource.url", "jdbc:h2:tcp://localhost/~/jbpm-db"));
        pds.getDriverProperties().put("driverClassName", properties.getProperty("persistence.datasource.driverClassName", "org.h2.Driver"));
        pds.init();
        return pds;
	}

}
