package com.mfy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mfy.util.LogTest;
import com.mfy.util.MailUtility;
import com.mybatis.UserService;

@Controller
@RequestMapping(value = "/test")
public class TestController implements ApplicationContextAware{

	static Logger LOG = LoggerFactory.getLogger(TestController.class);
	
	private ApplicationContext applicationContext;
	
	//http://localhost:8080/mfy-platform/test/completeProcess/123.do
    @RequestMapping( value="/completeProcess/{id}.do" )
    public String completeProcess(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
    	LOG.info("completeProcess:" + id + ", bean: " + this.applicationContext.getBean("emp"));
        
        UserService userService = (UserService)applicationContext.getBean("userService");
        LOG.info(String.valueOf(userService.countAll()));
        return "ok";
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException { 
		this.applicationContext = applicationContext;
	}
    
    @RequestMapping(value="/test") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(){
    	MailUtility mailUtility = (MailUtility) applicationContext.getBean("mailUtility");
        mailUtility.sendMail("huangcangbai");
        System.out.println("complete concurrencyOpt.");
        
    }
}


