package com.hcb.mq.main;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcb.web.filter.PostJsonParseFilter;

//import javax.jms.Connection;
//import javax.jms.ConnectionFactory;
//import javax.jms.Destination;
//import javax.jms.MapMessage;
//import javax.jms.MessageConsumer;
//import javax.jms.Session;
//
//import org.apache.activemq.ActiveMQConnectionFactory;

public class TestJmsReceiver {
	private static Logger LOG = LoggerFactory.getLogger(TestJmsReceiver.class);
	
//    public static void main(String[] args) throws Exception {  
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();  
//      
//        Connection connection = connectionFactory.createConnection();  
//        connection.start();  
//      
//        final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);  
//        Destination destination = session.createQueue("my-queue");  
//      
//        MessageConsumer consumer = session.createConsumer(destination);  
//        /*//listener 方式 
//        consumer.setMessageListener(new MessageListener() { 
//     
//            public void onMessage(Message msg) { 
//                MapMessage message = (MapMessage) msg; 
//                //TODO something.... 
//                LOG.info("收到消息：" + new Date(message.getLong("count"))); 
//                session.commit(); 
//            } 
//     
//        }); 
//        Thread.sleep(30000); 
//        */  
//        int i=0;  
//        while(i<3) {  
//            i++;  
//            MapMessage message = (MapMessage) consumer.receive();  
//            session.commit();  
//      
//            //TODO something....  
//            LOG.info("收到消息：" + new Date(message.getLong("count")));  
//        }  
//      
//        session.close();  
//        connection.close();  
//    }  
}
