package com.hcb.test.bpmrestful.delegate;

import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGenerateLeaveBillDelegate implements JavaDelegate {
  private static Logger LOG = LoggerFactory.getLogger(TestGenerateLeaveBillDelegate.class);
  
  public void execute(DelegateExecution execution) {
	  LOG.info("TestGenerateLeaveBillDelegate done.");
  }
  
}
