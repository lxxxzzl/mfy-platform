package com.mq.test.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestSpringJmsReceiver {
	
	private static Logger LOG = LoggerFactory.getLogger(TestSpringJmsReceiver.class);
	
//    public static void main(String[] args) {  
//        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:beanDefines*.xml");  
//        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");  
//        while(true) {  
//            Map<String, Object> mm =  (Map<String, Object>) jmsTemplate.receiveAndConvert();  
//            LOG.info("收到消息：" + new Date((Long)mm.get("count")));  
//        }  
//    }  
    
}
