package com.hcb.mc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hcb.mc.bean.MutliBean;
import com.hcb.mc.util.BeanUtil;

@Controller
@RequestMapping(value = "/hcb")
public class HcbController {

	@Resource
	private BeanUtil beanUtil;
	
    /**
     * http://localhost:8080/mfy-platform/hcb/test/1
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping( value="/test/{id}" )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
		System.out.println("beanUtil.getPlayer(): " + beanUtil.getPlayer());
		System.out.println("beanUtil.getPlayerList(): " + beanUtil.getPlayerList());
		System.out.println("beanUtil.getBeanInterfaceFactory(): " + beanUtil.getBeanInterfaceFactory());
		System.out.println("beanUtil.getBeanInterfaceFactory().size(): " + beanUtil.getBeanInterfaceFactory().size());
		
		MutliBean mutliBean = beanUtil.getBeanInterfaceFactory().getBean("a");
		mutliBean.methodA();
    }
	
}
