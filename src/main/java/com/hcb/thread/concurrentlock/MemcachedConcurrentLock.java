package com.hcb.thread.concurrentlock;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hcb.cache.MemcachedManager;

/**
 * 采用memcached方式实现的并发锁
 * 注意: 该接口的任何方法都不会抛任何异常，确保不中断正常业务功能
 * @author huangcangbai
 *
 */
@Service
public class MemcachedConcurrentLock implements ConcurrentLock {

	private static Logger LOG = LoggerFactory.getLogger(MemcachedConcurrentLock.class);
	
    /**
     * memchached客户端
     */
    @Resource
    private MemcachedManager memcachedManager;
    
	/**
	 * 缓存有效时长
	 */
	//TODO: set default value
    private int expiration = 15;
	
	/**
	 * 锁获取最大等待时间
	 */
	//TODO: set default value
	private int defalutMaxWaitTime = 200; 
	
	/**
	 * 获取锁重试时间间隔
	 */
	//TODO: set default value
	private int retryGetLockTime = 1; 
	
	/**
	 * 并发锁激活标记
	 */
	private boolean active = true;
	
	/**
	 * memcached中的item默认值 
	 */
	private final static int DEFAULT_ITEM_VALUE = 1;  
	
	@Override
	public boolean lock(String key) {
		return this.lock(key, defalutMaxWaitTime); 
	}

	@Override
	public boolean lock(String key, int maxWaitTime) {
		boolean lockSuccess = false;
		try {
			lockSuccess = this.lockUnCatchException(key, maxWaitTime);
		} catch (Throwable e) {
			LOG.error("get lock fail. key=" + key, e); 
		}
		return lockSuccess; 
	}

	/**
	 * 获取锁（不catch runtime exception）
	 * @param key
	 * @param maxWaitTime
	 * @return
	 */
	private boolean lockUnCatchException(String key, int maxWaitTime) { 
		if (!active) {
			LOG.error("get lock fail. key=" + key + ". Cause: MemcachedConcurrentLock not active."); 
			return false;
		}
		
		boolean lockSuccess = false;
		boolean connectSuccess = this.connect(3);
		if (!connectSuccess) {
			LOG.error("get lock fail. key=" + key + ". maxWaitTime="+ maxWaitTime + ". Cause: Memcached connect fail."); 
			return lockSuccess;
		}
		
		//想memcached添加item。并发场景下，只有一个客户端添加成功，其余的均返回false。
		//如果memcached中已存在了该key，那么返回false。
		lockSuccess =  memcachedManager.add(key, DEFAULT_ITEM_VALUE, expiration);
		if (!lockSuccess) {
			//获取锁失败
			int stepCount = maxWaitTime/retryGetLockTime;
			for (int i=0; i < stepCount; i++) {
				try {
					//单位毫秒
					Thread.sleep(retryGetLockTime * 1000);
				} catch (Exception e) {
					LOG.error("retryGetLockTime Thread.sleep error.",e);
				}
				
				boolean reAddSuccess =  memcachedManager.add(key, DEFAULT_ITEM_VALUE, expiration);
				if (reAddSuccess) {
					//重试之后，成功获得锁
					lockSuccess = true;
					break;
				} else {
					//重试之后，获得锁失败
					LOG.warn("retry lock fail.key=" + key + " count=" + (i+1));
				}
			}
		}
		
		if (lockSuccess) {
			//成功获得锁
			LOG.info("lock success.key=" + key); 
		} else {
			LOG.error("get lock fail. key=" + key + ". maxWaitTime="+ maxWaitTime); 
		}
		return lockSuccess;
	}

	/**
	 * 连接memcached 
	 * @param retries 重试次数
	 * @return 是否连接到memcached
	 */
	private boolean connect(int retries) {
		boolean connectSuccess = false;
		retries = retries < 0 ? 0 : retries;
		for (int i = retries; i >= 0; i--) {
			try {
				memcachedManager.connect();
				connectSuccess = true;
				break;
			} catch (Exception e) {
				LOG.error("连接memcached失败, retries=" + retries, e); 
			}
		}
		
		if (!connectSuccess) {
			LOG.error("连接memcached失败");
		}
		return connectSuccess;
	}
	
	@Override
	public boolean unlock(String key) {
		boolean unlockSuccess = false;
		boolean connectSuccess = false;
		try {
			connectSuccess = this.connect(3);
		} catch (Exception e1) {
		}
		
		if (!connectSuccess) {
			LOG.error("delete lock fail. key=" + key + ". Cause: Memcached connect fail."); 
			return unlockSuccess;
		}
		
		try {
			unlockSuccess = memcachedManager.delete(key);
		} catch (Exception e) {
			LOG.error("delete lock fail. key=" + key + ". Cause:", e);
		}
		
		if (unlockSuccess) {
			//成功删除锁
			LOG.info("delete lock success. key=" + key );
		} else {
			//锁删除失败
			LOG.error("delete lock fail. key=" + key );
		}
		return unlockSuccess;
	}

	public int getDefalutMaxWaitTime() {
		return defalutMaxWaitTime;
	}

	public void setDefalutMaxWaitTime(int defalutMaxWaitTime) {
		this.defalutMaxWaitTime = defalutMaxWaitTime;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
