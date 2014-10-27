package com.hcb.thread.concurrentlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcb.cache.KeyGenerator;

/**
 * memcached并发锁测试桩
 * @author huangcangbai
 *
 */
public class MemcachedConcurrentLockMock implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(MemcachedConcurrentLockMock.class);
		
	private final static int SLEEP_TIME = 2;
	
	private final static String KEY_PREFIX = "#RES.CFM#";
	
	/**
	 * 采用memcached方式实现的并发锁
	 */
	private MemcachedConcurrentLock memcachedConcurrentLock;
	
	public MemcachedConcurrentLockMock(MemcachedConcurrentLock memcachedConcurrentLock) {
		this.memcachedConcurrentLock = memcachedConcurrentLock;
	}
	
	@Override
	public void run() {
		String key = null;
		String prefix = KEY_PREFIX + "_" + "MemcachedConcurrentLockMock";
		int sequence = 101;
		try {
			key = KeyGenerator.generate(prefix, sequence);
		} catch (Exception e1) {
		}
		
		if (null != key) {
			memcachedConcurrentLock.lock(key);
			
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (Exception e) {
				LOG.error("MemcachedConcurrentLock Thread.sleep error.",e);
			}
			
			memcachedConcurrentLock.unlock(key);
		}
	}

}
