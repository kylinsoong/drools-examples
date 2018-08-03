package com.kylin.brms.jbpm.humantask.jpa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static final String PERSISTENCE_UNIT = "org.jbpm.task";
	
	private static EntityManagerFactory instance = null;
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return getEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public static EntityManagerFactory getEntityManagerFactory( String persistenceUnitName) {

		if(instance == null) {
			if(persistenceUnitName == null) {
				persistenceUnitName = PERSISTENCE_UNIT;
			}
			instance = Persistence.createEntityManagerFactory(persistenceUnitName);
		}
		
		return instance;
	}

	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnit, String dbProps){
		
		if(instance == null) {
			
			if(dbProps == null || ! (new File(dbProps).exists())) {
				throw new RuntimeException(" Database Connection Properties con not be found");
			}
			
			if(persistenceUnit == null) {
				persistenceUnit = PERSISTENCE_UNIT;
			}
			
			 Properties prop = new Properties();  
			 
			 InputStream in = null;
			 
			 try {
				in = new FileInputStream(new File(dbProps));
				prop.load(in);
			} catch (Exception e) {
				throw new RuntimeException("Init EntityManagerFactory error");
			} finally {
				if(null != in) {
					try {
						in.close();
					} catch (IOException e) {
						throw new RuntimeException("Init EntityManagerFactory error");
					}
				}
			}
			 
			 instance = Persistence.createEntityManagerFactory(persistenceUnit, prop);
		}
		
		return instance;
	}
}
