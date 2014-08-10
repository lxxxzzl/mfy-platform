package com.hcb.mq.spring;

import java.util.Date;

//import javax.jms.JMSException;
//import javax.jms.MapMessage;
//import javax.jms.Message;
//import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class TestSpringJmsSender {
	
    public static void main(String[] args) throws Exception {
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:beanDefines*.xml");  
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");  
//        jmsTemplate.send(new MessageCreator() {  
//            public Message createMessage(Session session) throws JMSException {  
//                MapMessage mm = session.createMapMessage();  
//                mm.setLong("count", new Date().getTime());  
//                return mm;  
//            }  
//        });  
    }  
    
}
