package com.kylin.brms.jbpm.humantask.jpa;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.jbpm.task.User;
import org.junit.BeforeClass;
import org.junit.Test;

public class BRMSjBPMHumanTaskDBTableTest {

	private static EntityManagerFactory factory = null;
	
	@BeforeClass  
    public static void beforeClass(){
		factory = JPAUtil.getEntityManagerFactory();
	}
	
	@Test  
    public void save() {  
		
		EntityManager em = factory.createEntityManager();
        EntityTransaction t = em.getTransaction();  
        t.begin();  
        
        User user = new User();
        user.setId("111");
        em.persist(user);
        t.commit();  
    }   
	
	public static void main(String[] args) {
		factory = JPAUtil.getEntityManagerFactory();
		EntityManager em = factory.createEntityManager();
        EntityTransaction t = em.getTransaction();  
        t.begin();  
        
        User user = new User();
        user.setId("111");
        em.persist(user);
        t.commit();  
	}
}
