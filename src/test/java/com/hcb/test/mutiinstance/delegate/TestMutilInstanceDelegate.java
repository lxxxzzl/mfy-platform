package com.hcb.test.mutiinstance.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hcb.test.oneelement.delegate.TestOneJobDelegate;

@Service
public class TestMutilInstanceDelegate implements JavaDelegate {
	private static Logger LOG = LoggerFactory.getLogger(TestOneJobDelegate.class);
	
	public void execute(DelegateExecution execution) throws Exception { 
		
		
		LOG.info("-----------------TestMutilInstanceDelegate------------" + execution.getVariables());
	}

}
