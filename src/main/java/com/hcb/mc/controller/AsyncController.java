package com.hcb.mc.controller;

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

import com.hcb.mybatis.UserService;
import com.hcb.util.MailUtility;

@Controller
@RequestMapping(value = "/bpmAsyncTest")
public class AsyncController implements ApplicationContextAware{

	static Logger LOG = LoggerFactory.getLogger(AsyncController.class);
	
	private ApplicationContext applicationContext;
	
	@Resource
	private MailUtility mailUtility;
	
	//http://localhost:8080/mfy-platform/bpmAsyncTest/completeProcess/123.do
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
     * localhost:8080/mfy-platform/bpmAsyncTest/asyncSendMail
     */
    @RequestMapping(value="/asyncSendMail") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void asyncSendMail(){
        mailUtility.sendMail("huangcangbai");
        LOG.info("complete concurrencyOpt.");
    }
    
    /**
     * http://localhost:8080/mfy-platform/bpmAsyncTest/testAsyncResp/
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    @RequestMapping(value="/testAsyncResp") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void testAsyncResp() throws InterruptedException, ExecutionException{
    	long startTime = System.currentTimeMillis();
    	MailUtility mailUtility = (MailUtility) applicationContext.getBean("mailUtility");
    	Future<String> future = mailUtility.longOpt();
    	
    	//如果spring异步task配置ok的话，下面这句日志与longOpt方法并行执行。
    	//否则，面这句日志将在longOpt方法执行完毕后才打印
    	LOG.info("other operate" );
        
    	String asyncResp = future.get();
    	LOG.info("end get AsyncResp:" + asyncResp);
        LOG.info("complete testAsyncResp. time is: " + (System.currentTimeMillis()-startTime)+"ms");
    }
    
}


