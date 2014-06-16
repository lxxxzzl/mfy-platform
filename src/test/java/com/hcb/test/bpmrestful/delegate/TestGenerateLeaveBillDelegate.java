package com.hcb.test.bpmrestful.delegate;

import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;

public class TestGenerateLeaveBillDelegate implements JavaDelegate {
  
  public void execute(DelegateExecution execution) {
	  System.out.println("TestGenerateLeaveBillDelegate done.");
  }
  
}
