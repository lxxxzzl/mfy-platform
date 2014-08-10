package com.hcb.test.oneelement.delegate;

import java.util.Map;

import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestOneJobDelegate implements JavaDelegate {

  private static Logger LOG = LoggerFactory.getLogger(TestOneJobDelegate.class);
	
  public void execute(DelegateExecution execution) {
	  Map<String, Object> variables = execution.getVariables();
	  LOG.info("-------- TestOneJobDelegate ----------" + variables.get("orderId")); 
  }
  
}
