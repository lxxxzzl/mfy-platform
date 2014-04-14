package com.mq.test.spring;

import java.util.Date;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class TestSpringJmsReceiver {
	
//    public static void main(String[] args) {  
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:beanDefines*.xml");  
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");  
//        while(true) {  
//            Map<String, Object> mm =  (Map<String, Object>) jmsTemplate.receiveAndConvert();  
//            System.out.println("收到消息：" + new Date((Long)mm.get("count")));  
//        }  
//    }  
    
}
