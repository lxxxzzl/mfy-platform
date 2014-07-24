package com.hcb.test.cfm;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;

public class ConfirmTest extends PluggableActivitiTestCase {
  
  @Deployment
  public void testTask() {
	Map<String, Object> variables = new HashMap<String,Object>();
	variables.put("orderId", 10001000);
	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("confirmMainProcess", variables);
    assertNotNull(processInstance);
  }
  
  
}
