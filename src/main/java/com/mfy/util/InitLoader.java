package com.mfy.util;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class InitLoader implements ApplicationContextAware {
	
	static Logger LOG = LoggerFactory.getLogger(InitLoader.class);
	
	private ApplicationContext applicationContext;
	
	
	public void init(){
		this.initMQ();
	}
 
	
	@PostConstruct
	public void postInit(){
		LOG.info("InitLoader postConstruct");
	}
 
	
	private void initMQ() {
		LOG.info("InitLoader initMQ");
//		JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");  
//        while(true) {  
//            Map<String, Object> mm =  (Map<String, Object>) jmsTemplate.receiveAndConvert();  
//            LOG.info("收到消息：" + new Date((Long)mm.get("count")));  
//        } 
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
