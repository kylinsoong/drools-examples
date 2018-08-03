package com.sample.amq;

import static com.sample.cxf.Constants.*;

import java.util.Arrays;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class LogisticsSender {
   
    public static void main(String[] args) throws JMSException, InterruptedException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://172.30.254.157:61616");
        factory.setUserName("admin");
        factory.setPassword("admin");
        Connection conn = factory.createConnection();
        conn.start();

        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("logistics.newFreight");
        MessageProducer messageProducer = session.createProducer(queue);
        
        Arrays.asList(commands).forEach(c -> {
            try {
                TextMessage message = session.createTextMessage(c);
                messageProducer.send(message, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
                System.out.println("SEND NEW FREIGHT: " + message.getText());
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        

        session.close();
        conn.close();
}

    

}
