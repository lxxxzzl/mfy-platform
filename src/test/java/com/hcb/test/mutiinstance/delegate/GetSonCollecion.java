package com.hcb.test.mutiinstance.delegate;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcb.test.oneelement.delegate.TestOneJobDelegate;

//@Service
public class GetSonCollecion {

	private static Logger LOG = LoggerFactory.getLogger(TestOneJobDelegate.class);
	
	public List<String> getSonCollecion() {
		LOG.info("-------------------GetSonCollecion---------------");
		return Arrays.asList("big son","mid son","small son");
	}
	
}
