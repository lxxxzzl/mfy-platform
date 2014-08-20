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

import com.hcb.mc.vo.User;
import com.hcb.mybatis.UserDao;


@Controller
@RequestMapping(value = "/db-test")
public class DBTestController {

	private static Logger LOG = LoggerFactory.getLogger(DBTestController.class);
	
	@Resource 
	UserDao userDao;
	
    /**
     * 测试PostParserHttpServletRequestWrapper过滤器（解析post请求的消息体到request的parameter对象中）
     * http://localhost:8080/mfy-platform/db-test/dao-test
     * @param request
     * @param response
     */
    @RequestMapping(value="/dao-test", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void daoTest(HttpServletRequest request,HttpServletResponse response){
    	LOG.info("userDao.countAll():" + userDao.countAll());
    	
    	User toAddUser = new User(); 
    	toAddUser.setName("newInsertName"); 
    	userDao.insert(toAddUser);
    	LOG.info("userDao.insert execute.");
    	
    	LOG.info("userDao.countAll():" + userDao.countAll());
    	
    	
    	LOG.info("dao-test execute ok.");
    }
	
}
