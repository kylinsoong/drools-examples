package com.sample.amq;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.sample.cxf.Constants;

public class LogisticsReceiver {

    public static void main(String[] args) throws JMSException, InterruptedException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://172.30.254.157:61616");
        factory.setUserName("admin");
        factory.setPassword("admin");
        Connection conn = factory.createConnection();
        conn.start();

        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("logistics.results");
        MessageConsumer messageConsumer = session.createConsumer(queue);
        
        for(int i = 0 ; i < Constants.commands.length ; i ++) {
            BytesMessage receivedMessage = (BytesMessage) messageConsumer.receive(5000L);
            if (receivedMessage != null) {
                int lehgth = (int) receivedMessage.getBodyLength(); 
                byte[] buf = new byte[lehgth];
                receivedMessage.readBytes(buf);
                System.out.println("BRM RESPONSE: " +new String(buf) + "\n");
            } else {
                System.out.println("No message received within the given timeout!");
            }
            Thread.sleep(1000 * 10);
        }

        session.close();
        conn.close();
    }

}
