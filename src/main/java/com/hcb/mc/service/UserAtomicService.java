package com.hcb.mc.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcb.mc.controller.DBTestController;
import com.hcb.mc.vo.User;
import com.hcb.mybatis.UserDao;

@Service
public class UserAtomicService {

	private static Logger LOG = LoggerFactory.getLogger(DBTestController.class);
	
	@Resource 
	UserDao userDao;
	
	@Resource
	private TaskExecutor taskExecutor;
	
	/**
	 * 插入数据
	 * @param name
	 */
	private void insert(String name) { 
    	User toAddUser = new User(); 
    	toAddUser.setName("newInsertName"); 
    	userDao.insert(toAddUser);
    	LOG.info("userDao.insert execute." );
	}
	
	
	/**
	 * 查询数据
	 * @param name
	 */
	private void query(String name) {
    	LOG.info(name + " --------userDao.countAll():" + userDao.countAll());
	}
	
	/**
	 * 模拟并发反馈问题
	 * 查询不到数据库事务未完成提交的记录
	 * @param name
	 */
	@Transactional
	public void testConcurrentFeedbackProblem(String name) { 
		this.insert(name); 
		this.query(name);
	}
	
	/**
	 * 解决并发反馈问题
	 * @param name
	 */
	@Transactional
	public void testFixConcurrentFeedbackProblem(final String name) {
		this.insert(name); 
		taskExecutor.execute(new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				query(name);
			}
		});
	}
	
}
