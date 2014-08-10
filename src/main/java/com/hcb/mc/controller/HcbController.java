package com.hcb.mc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hcb.mc.bean.MutliBean;
import com.hcb.mc.bean.MutliBeanB;
import com.hcb.mc.bean.relation.AbstractBase;
import com.hcb.mc.bean.relation.ConcreteA;
import com.hcb.mc.util.BeanUtil;

@Controller
@RequestMapping(value = "/hcb")
public class HcbController {

	private static Logger LOG = LoggerFactory.getLogger(HcbController.class);
	
	@Resource
	private BeanUtil beanUtil;
	
	@Resource
	private AbstractBase abstractBase;
	
    /**
     * http://localhost:8080/mfy-platform/hcb/test/1
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping( value="/test/{id}" )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void test(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
//		LOG.info("beanUtil.getPlayer(): " + beanUtil.getPlayer());
//		LOG.info("beanUtil.getPlayerList(): " + beanUtil.getPlayerList());
//		LOG.info("beanUtil.getBeanInterfaceFactory(): " + beanUtil.getBeanInterfaceFactory());
//		LOG.info("beanUtil.getBeanInterfaceFactory().size(): " + beanUtil.getBeanInterfaceFactory().size());
//		
//		MutliBean mutliBean = beanUtil.getBeanInterfaceFactory().getBean("a");
//		mutliBean.methodA();
		
		LOG.info("this.abstractBase.whoami:" + this.abstractBase.whoami());
		//LOG.info("this.concrete.whoami:" + this.concrete.whoami());
    }
	
}
