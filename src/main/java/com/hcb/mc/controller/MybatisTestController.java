package com.hcb.mc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hcb.mc.service.UserAtomicService;
import com.hcb.mc.vo.User;


@Controller
@RequestMapping(value = "/mybatis-test")
public class MybatisTestController {

	private static Logger LOG = LoggerFactory.getLogger(MybatisTestController.class);
	
	@Resource 
	UserAtomicService userAtomicService;
	
    /**
     * 测试并发事务访问同时读写，读不完整问题
     * http://localhost:8080/mfy-platform/mybatis-test/common-test
     * @param request
     * @param response
     */
    @RequestMapping(value="/common-test", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void commonTest(HttpServletRequest request,HttpServletResponse response){
    	User toAddUser = new User();
    	toAddUser.setName("aa");
    	userAtomicService.insert(toAddUser);
    	LOG.info("mybatisTest execute ok. id:" + toAddUser.getId());
    }
  
	
}
