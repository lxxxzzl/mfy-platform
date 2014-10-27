package com.hcb.thread.controller;


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

import com.hcb.cache.MemcachedManager;
import com.hcb.thread.concurrentlock.ConcurrentMockV1;
import com.hcb.thread.concurrentlock.MemcachedConcurrentLock;
import com.hcb.thread.concurrentlock.MemcachedConcurrentLockMock;


@Controller
@RequestMapping(value = "/memcached-test")
public class MemcachedTestController {

	private static Logger LOG = LoggerFactory.getLogger(MemcachedTestController.class);
	
    @Resource
    private MemcachedManager memcachedManager;
	
    @Resource
    private TaskExecutor taskExecutorTest;
    
    @Resource
    private MemcachedConcurrentLock memcachedConcurrentLock;
    
    /**
     * 测试memcached基本操作
     * http://localhost:8080/mfy-platform/memcached-test/simple/123.do
     * @param request
     * @param response
     */
    @RequestMapping(value="/simple/{param}.do", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void daoTest(HttpServletRequest request,HttpServletResponse response,@PathVariable String param){
    	try {
			memcachedManager.connect();
		} catch (Exception e) {
			LOG.error("连接memcached失败",e); 
		}
    	
    	String key = "CFM.confirm" + "_" + "memcached-test_simple" + "2014-09-16";
    	LOG.info("memcached-test/simple init mem-value=" + memcachedManager.get(key));	
    	
    	int value_1 = 1;
		boolean addState_1 = memcachedManager.add(key, value_1, 30);
		LOG.info("memcached-test/simple mem-value=" + memcachedManager.get(key) + " addState="+addState_1);
		
		boolean deleteState= memcachedManager.delete(key);
		LOG.info("memcached-test/simple deleteState=" + deleteState);
		LOG.info("memcached-test/simple init mem-value=" + memcachedManager.get(key));	
		
		boolean deleteState2= memcachedManager.delete(key);
		LOG.info("memcached-test/simple deleteState2=" + deleteState2);
		LOG.info("memcached-test/simple init mem-value=" + memcachedManager.get(key));	
		
		int value_2 = 2;
		boolean addState_2 = memcachedManager.add(key, value_2, 30);
    	LOG.info("memcached-test/simple mem-value=" + memcachedManager.get(key)+ " addState="+addState_2);
    	
    	LOG.info("memcachedManager.getTimeOut=" + memcachedManager.getTimeOut());
    }
    
    /**
     * 测试memcached并发操作
     * http://localhost:8080/mfy-platform/memcached-test/concurrent/123.do
     * @param request
     * @param response
     */
    @RequestMapping(value="/concurrent/{param}.do", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void concurrentTest(HttpServletRequest request,HttpServletResponse response,@PathVariable String param) {
    	try {
			memcachedManager.connect();
		} catch (Exception e) {
			LOG.error("连接memcached失败",e); 
		}
    	
    	for (int i=0; i < 10; i++) {
	    	taskExecutorTest.execute(new ConcurrentMockV1(i, memcachedManager));
    	}
    }
    
    /**
     * 测试memcachedlock并发锁
     * http://localhost:8080/mfy-platform/memcached-test/memcachedlock/123.do
     * @param request
     * @param response
     */
    @RequestMapping(value="/memcachedlock/{param}.do", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void memcachedlock(HttpServletRequest request,HttpServletResponse response,@PathVariable String param) {
    	for (int i=0; i < 10; i++) {
    		Runnable lockMock = new MemcachedConcurrentLockMock(this.memcachedConcurrentLock);
	    	taskExecutorTest.execute(lockMock);
    	}
    }
}



