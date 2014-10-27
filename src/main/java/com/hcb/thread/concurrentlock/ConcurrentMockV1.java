package com.hcb.thread.concurrentlock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcb.cache.MemcachedManager;

public class ConcurrentMockV1 implements Runnable {

	private static Logger LOG = LoggerFactory.getLogger(ConcurrentMockV1.class);
	
    private MemcachedManager memcachedManager;
    
	private Object name;
	
	public ConcurrentMockV1() {
	}
	
	public ConcurrentMockV1(Object name, MemcachedManager memcachedManager) {
		this.name = name;
		this.memcachedManager = memcachedManager;
	}
	
	@Override
	public void run() {
		this.memcachedOpt();
	}
	
	private void memcachedOpt() {
		String key = "CFM.confirm" + "_" + "memcached-test_concurrent" + "2014-09-16";
		Object valueInit = memcachedManager.get(key);
		if (null != valueInit) {
			LOG.info("  " + name + "   -----------------init memcachedManager.get(key) not null----------------------------");
		}
    	
    	int value = 1;
		boolean addState = memcachedManager.add(key, value, 5);
		
		if (addState) {
			LOG.info("  " + name + "   -----------------------------------------------------------------              ");
			LOG.info("  " + name + "   >>>>>>>>>>>>>>>>>>>>>> addState "+addState);
			LOG.info("  " + name + "   ----------------------------------------------------------------              ");
		} else {
			LOG.info("  " + name + "   addState="+addState);
		}
	}
}
