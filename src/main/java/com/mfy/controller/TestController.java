package com.mfy.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
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

import com.mfy.util.MailUtility;
import com.mybatis.UserService;

@Controller
@RequestMapping(value = "/test")
public class TestController implements ApplicationContextAware{

	static Logger LOG = LoggerFactory.getLogger(TestController.class);
	
	private ApplicationContext applicationContext;
	
	@Resource
	private MailUtility mailUtility;
	
	//http://localhost:8080/mfy-platform/test/completeProcess/123.do
    @RequestMapping( value="/completeProcess/{id}.do" )
    public String completeProcess(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
    	LOG.info("completeProcess:" + id );
        UserService userService = (UserService)applicationContext.getBean("userService");
        LOG.info(String.valueOf(userService.countAll()));
        return "ok";
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException { 
		this.applicationContext = applicationContext;
	}
    
    /**
     * localhost:8080/mfy-platform/test/
     */
    @RequestMapping(value="/test") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(){
        mailUtility.sendMail("huangcangbai");
        System.out.println("complete concurrencyOpt.");
    }
    
    /**
     * http://localhost:8080/mfy-platform/test/testAsyncResp/
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @RequestMapping(value="/testAsyncResp") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void testAsyncResp() throws InterruptedException, ExecutionException{
    	long startTime = System.currentTimeMillis();
    	MailUtility mailUtility = (MailUtility) applicationContext.getBean("mailUtility");
    	Future<String> future = mailUtility.longOpt();
//    	String asyncResp = future.get();
//    	System.out.println("end get AsyncResp:" + asyncResp);
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String asyncResp = future.get();
    	System.out.println("end get AsyncResp:" + asyncResp);
        System.out.println("complete testAsyncResp. time is: " + (System.currentTimeMillis()-startTime)+"ms");
    }
    
}


