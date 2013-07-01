/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.test.lifecycle;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import org.drools.SystemEventListenerFactory;
import org.jbpm.task.Group;
import org.jbpm.task.User;
import org.jbpm.task.service.EscalatedDeadlineHandler;
import org.jbpm.task.service.SendIcal;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.local.*;
import org.jbpm.test.lifecycle.mock.MockUserInfo;
import org.junit.After;
import org.junit.Before;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.compiler.ExpressionCompiler;

public abstract class BaseTest {
    protected EntityManagerFactory emf;

    protected Map<String, User> users;
    protected Map<String, Group> groups;

    protected TaskService taskService;
    protected LocalTaskService localTaskService;
    
    protected TaskServiceSession taskSession;
    
    protected MockUserInfo userInfo;
    
    protected Properties conf;
    
    protected void println(Object obj) {
    	System.out.println("\n\t" + obj + "\n");
    }

    
    @Before
    public void setUp() throws Exception {
    	
        conf = new Properties();
        conf.setProperty("mail.smtp.host", "localhost");
        conf.setProperty("mail.smtp.port", "1125");
        conf.setProperty("from", "from@domain.com");
        conf.setProperty("replyTo", "replyTo@domain.com");
        conf.setProperty("defaultLanguage", "en-UK");
        SendIcal.initInstance(conf);

        // Use persistence.xml configuration
        emf = Persistence.createEntityManagerFactory("org.jbpm.task");

        Reader reader = null;
        Map vars = new HashMap();
        try {
            reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("LoadUsers.mvel"));
            users = (Map<String, User>) eval(reader, vars);
        } finally {
            if (reader != null) reader.close();
            reader = null;
        }
        
        try {
            reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("LoadGroups.mvel"));
            groups = (Map<String, Group>) eval(reader,  vars);
        } finally {
            if (reader != null) reader.close();
        }
        
        userInfo = new MockUserInfo();
        
        taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener(),this.getEscalatedDeadlineHandler());
        taskSession = taskService.createSession();
        
        taskService.setUserinfo(userInfo);

        for (User user : users.values()) {
            taskSession.addUser(user);
        }
        
        for (Group group : groups.values()) {
            taskSession.addGroup(group);
        }
 
        localTaskService = new LocalTaskService(taskService);
    }

    @After
    public void tearDown() throws Exception {
        
        System.out.println("Disposing Local Task Service session");
        localTaskService.disconnect();
        
        System.out.println("Disposing session");
        taskSession.dispose();
        emf.close();
        
        Thread.sleep(1000);
    }

    public Object eval(Reader reader, Map vars) {
        try {
            return eval(toString(reader), vars);
        } catch (IOException e) {
            throw new RuntimeException("Exception Thrown", e);
        }
    }

    public String toString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder(1024);
        int charValue;

        while ((charValue = reader.read()) != -1) {
            sb.append((char) charValue);
        }
        return sb.toString();
    }

    public Object eval(String str, Map vars) {
        ExpressionCompiler compiler = new ExpressionCompiler(str.trim());

        ParserContext context = new ParserContext();
        context.addPackageImport("org.jbpm.task");
        context.addPackageImport("org.jbpm.task.service");
        context.addPackageImport("org.jbpm.task.query");
        context.addPackageImport("java.util");

        vars.put("now", new Date());
        return MVEL.executeExpression(compiler.compile(context), vars);
    }
    
    public EscalatedDeadlineHandler getEscalatedDeadlineHandler(){
        //by default, do not provide any handler
        return null;
    }
}
