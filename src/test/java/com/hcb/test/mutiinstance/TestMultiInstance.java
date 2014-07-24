package com.hcb.test.mutiinstance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.test.Deployment;

public class TestMultiInstance extends PluggableActivitiTestCase {

	@Deployment(resources = {
		"com/hcb/test/multiinstance/parentProcess.bpmn",
		"com/hcb/test/multiinstance/sonProcess.bpmn"})
	public void test() {
		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("sons", Arrays.asList("big son","mid son","small son"));
		ProcessInstance proc = runtimeService.startProcessInstanceByKey("parentProcess",variables);
		
		ProcessInstanceQuery procQuery = runtimeService.createProcessInstanceQuery().superProcessInstanceId(proc.getId());
		System.out.println("----------procQuery-------" + procQuery.count());
		
	}
	
}
