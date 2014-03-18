package com.mfy.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mybatis.UserService;

@Controller
@RequestMapping(value = "/test")
public class TestController implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	//http://localhost:8080/mfy-platform/test/completeProcess/123.do
    @RequestMapping( value="/completeProcess/{id}.do" )
    public String completeProcess(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
        System.out.println("开始完成流程:" + id + ", bean: " + this.applicationContext.getBean("emp"));
        
        UserService userService = (UserService)applicationContext.getBean("userService");
        System.out.println(userService.countAll());
        return "ok";
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
    
}


