package com.hcb.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * key值生成器
 * @author huangcangbai
 *
 */
public class KeyGenerator {

	private static Logger LOG = LoggerFactory.getLogger(KeyGenerator.class);
	
	/**
	 * 生成key
	 * @param prefix 前缀。格式要求：系统名_操作名
	 * @param sequence
	 * @return
	 * @throws Exception
	 */
	public static String generate(String prefix, Integer sequence) throws Exception {
		if (null == prefix || "".equals(prefix.trim())) {
			LOG.error("KeyGenerator generate fail: prefix is empty");
			throw new Exception("KeyGenerator generate fail: prefix is empty");
		} 
		
		if (null == sequence || 0 == sequence) {
			LOG.error("KeyGenerator generate fail: sequence is 0");
			throw new Exception("KeyGenerator generate fail: sequence is 0");
		}
		
		return prefix + "_" + sequence;
	}
}
