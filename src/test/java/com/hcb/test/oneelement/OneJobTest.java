package com.hcb.test.oneelement;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.test.Deployment;

public class OneJobTest extends PluggableActivitiTestCase {
  
  @Deployment
  public void testTask() {
	Map<String, Object> variables = new HashMap<String,Object>();
	variables.put("orderId", 10001000);
	runtimeService.startProcessInstanceByKey("testOneJobProcess", variables);
  }
  
  
}
