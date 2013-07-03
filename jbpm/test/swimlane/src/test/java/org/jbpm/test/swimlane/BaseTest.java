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

package org.jbpm.test.swimlane;

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
import org.jbpm.task.service.SendIcal;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskServer;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.jbpm.test.swimlane.mock.MockUserInfo;
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
    protected TaskServiceSession taskSession;
    
    protected TaskServer server;
    protected TaskClient client;

    @Before
    public void setUp() throws Exception {
        Properties conf = new Properties();
        conf.setProperty("mail.smtp.host", "localhost");
        conf.setProperty("mail.smtp.port", "2345");
        conf.setProperty("from", "from@domain.com");
        conf.setProperty("replyTo", "replyTo@domain.com");
        conf.setProperty("defaultLanguage", "en-UK");
        SendIcal.initInstance(conf);

        // Use persistence.xml configuration
        emf = Persistence.createEntityManagerFactory("org.jbpm.task");

        taskService = new TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
        taskSession = taskService.createSession();
        MockUserInfo userInfo = new MockUserInfo();
        taskService.setUserinfo(userInfo);
        Map vars = new HashMap();

        Reader reader = null;

        try {
            reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("LoadUsers.mvel"));
            users = (Map<String, User>) eval(reader, vars);
            for (User user : users.values()) {
                taskSession.addUser(user);
            }
        } finally {
            if (reader != null) reader.close();
            reader = null;
        }
        
        try {
            reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("LoadGroups.mvel"));
            groups = (Map<String, Group>) eval(reader,  vars);
            for (Group group : groups.values()) {
                taskSession.addGroup(group);
            }
        } finally {
            if (reader != null) reader.close();
        }
        
        server = new MinaTaskServer(taskService);
        Thread thread = new Thread(server);
        thread.start();
        System.out.println("Waiting for the MinaTask Server to come up");
        while (!server.isRunning()) {
        	System.out.print(".");
        	Thread.sleep( 50 );
        }

        client = new TaskClient(new MinaTaskClientConnector("client 1",
                                     new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        client.connect("127.0.0.1", 9123);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Disconnectig client");
        client.disconnect();
        
        System.out.println("Disposing session");
        taskSession.dispose();
        emf.close();
        
        System.out.println("Stopping server");
        server.stop();
    }

    public Object eval(Reader reader,
                       Map vars) {
        try {
            return eval(toString(reader),
                    vars);
        } catch (IOException e) {
            throw new RuntimeException("Exception Thrown",
                    e);
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
}
