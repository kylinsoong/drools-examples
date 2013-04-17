package org.jbpm.quickstarts.usertask.util;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SuppressWarnings("rawtypes")
public class JPAUtil {
	
	private static EntityManagerFactory emf = null;
	
	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName){
		if(null == emf) {
			emf = Persistence.createEntityManagerFactory(persistenceUnitName);
		}
		return emf;
	}
	
	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName, Map map){
		if(null == emf) {
			emf = Persistence.createEntityManagerFactory(persistenceUnitName, map);
		}
		return emf;
	}
	
	public static EntityManager getEntityManager(String persistenceUnitName, Map map) {
		return getEntityManagerFactory(persistenceUnitName, map).createEntityManager();
	}
	
	public static EntityManager getEntityManager(String persistenceUnitName) {
		return JPAUtil.getEntityManagerFactory("com.customized.tools.persist.test").createEntityManager();
	}
	
	public static void destory() {
		if(null == emf) {
			emf.close();
			emf = null;
		}
	}

}
