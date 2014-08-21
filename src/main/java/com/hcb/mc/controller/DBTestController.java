package com.hcb.mc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hcb.mc.service.UserAtomicService;


@Controller
@RequestMapping(value = "/db-test")
public class DBTestController {

	private static Logger LOG = LoggerFactory.getLogger(DBTestController.class);
	
	@Resource 
	UserAtomicService userAtomicService;
	
	@Resource
	private TaskExecutor taskExecutor;
	
    /**
     * 测试PostParserHttpServletRequestWrapper过滤器（解析post请求的消息体到request的parameter对象中）
     * http://localhost:8080/mfy-platform/db-test/dao-test
     * @param request
     * @param response
     */
    @RequestMapping(value="/dao-test", method =RequestMethod.GET)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void daoTest(HttpServletRequest request,HttpServletResponse response){
    	
    	this.fixConcurrentFeedbackProblem();
    	
    	LOG.info("dao-test execute ok.");
    }
    
	public void fixConcurrentFeedbackProblem() {
		for (int i = 0; i < 2; i++) {
			taskExecutor.execute(new UserServiceTask("Message" + i));
			
		}
	}

	private class UserServiceTask implements Runnable { 
		private String message;

		public UserServiceTask(String message) {
			this.message = message;
		}

		public void run() {
			//模拟并发反馈问题
			//查询不到数据库事务未完成提交的记录
			//userAtomicService.testConcurrentFeedbackProblem(message);
			
			//解决并发反馈问题
			userAtomicService.testFixConcurrentFeedbackProblem(message);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
