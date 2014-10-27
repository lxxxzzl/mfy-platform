package com.hcb.thread.concurrentlock;

/**
 * 并发锁
 * 注意: 该接口的任何方法都不会抛任何异常，确保不中断正常业务功能
 * @author huangcangbai
 *
 */
public interface ConcurrentLock {
	
	/**
	 * 将指定的key值的item锁定
	 * @param key
	 * @return 是否锁定成功
	 */
	boolean lock(String key);
	
	/**
	 * 将指定的key值的item锁定
	 * @param key
	 * @param maxWaitTime 锁获取最大等待时间(单位s)
	 * @return 是否锁定成功
	 */
	boolean lock(String key, int maxWaitTime);
	
	/**
	 * 将指定的key值的item解锁
	 * @param key
	 * @return 是否解锁成功
	 */
	boolean unlock(String key);
	
	
	/**
	 * 同步锁激活标志
	 * @param active
	 */
	void setActive(boolean active);
}
