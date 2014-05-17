package com.hcb.mc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hcb.mc.bean.MutliBean;
import com.hcb.mc.util.BeanUtil;

@Controller
@RequestMapping(value = "/hcb")
public class HcbController {

    @RequestMapping( value="/test/{id}" )
    public String test(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
    	BeanUtil beanUtil = (BeanUtil) BeanUtil.applicationContext.getBean("beanUtil");
		
		System.out.println("beanUtil.getPlayer(): " + beanUtil.getPlayer());
		System.out.println("beanUtil.getPlayerList(): " + beanUtil.getPlayerList());
		System.out.println("beanUtil.getBeanInterfaceFactory(): " + beanUtil.getBeanInterfaceFactory());
		System.out.println("beanUtil.getBeanInterfaceFactory().size(): " + beanUtil.getBeanInterfaceFactory().size());
		
		MutliBean mutliBean = beanUtil.getBeanInterfaceFactory().getBean("a");
		mutliBean.methodA();
        return "ok";
    }
	
}
