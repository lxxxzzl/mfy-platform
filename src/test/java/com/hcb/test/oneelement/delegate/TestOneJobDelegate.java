package com.hcb.test.oneelement.delegate;

import java.util.Map;

import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;

public class TestOneJobDelegate implements JavaDelegate {
  
  public void execute(DelegateExecution execution) {
	  Map<String, Object> variables = execution.getVariables();
	  System.out.println("-------- TestOneJobDelegate ----------" + variables.get("orderId")); 
  }
  
}
