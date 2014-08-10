package com.hcb.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/thread")
public class ThreadController implements ApplicationContextAware{

	static Logger LOG = LoggerFactory.getLogger(ThreadController.class);
	
	private ApplicationContext applicationContext;
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException { 
		this.applicationContext = applicationContext;
	}
    
    /**
     * http://localhost:8080/mfy-platform/thread/test/
     */
    @RequestMapping(value="/test") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(){
    	TaskExecutorExample te = (TaskExecutorExample)applicationContext.getBean("taskExecutorExample");   
    	te.printMessages();   
    	LOG.info("新请求进入");  
    }
    
}


