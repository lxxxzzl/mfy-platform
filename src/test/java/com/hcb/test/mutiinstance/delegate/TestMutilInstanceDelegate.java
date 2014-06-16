package com.hcb.test.mutiinstance.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class TestMutilInstanceDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception { 
		
		
		System.out.println("-----------------TestMutilInstanceDelegate------------" + execution.getVariables());
	}

}
