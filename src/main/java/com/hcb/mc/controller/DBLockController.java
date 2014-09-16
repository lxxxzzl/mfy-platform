package com.hcb.mc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hcb.mc.service.UserAtomicService;
import com.hcb.mybatis.UserDao;


@Controller
@RequestMapping(value = "/db-lock")
public class DBLockController {

	private static Logger LOG = LoggerFactory.getLogger(DBLockController.class);
	
	@Resource 
	UserDao userDao;
	
    /**
     * 测试GET_LOCK
     * http://localhost:8080/mfy-platform/db-lock/get-lock-test/123.do
     * @param request
     * @param response
     */
    @RequestMapping(value="/get-lock-test/{key}.do", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void daoTest(HttpServletRequest request,HttpServletResponse response,@PathVariable String key){
    	int getLock = userDao.selectGetLockByKey(key, 1);
    	LOG.info("get-lock-test execute ok. getLock=" + getLock);
    }
    
	
	
}
