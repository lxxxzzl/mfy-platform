package com.mfy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

	static Logger LOG = LoggerFactory.getLogger(LogTest.class);
	
	public static void main(String[] args) {
		LOG.info("log back test.");
	}

}
