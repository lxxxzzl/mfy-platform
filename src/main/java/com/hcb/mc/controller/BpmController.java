package com.hcb.mc.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/bpm")
public class BpmController {

	static Logger LOG = LoggerFactory.getLogger(BpmController.class);
	
	@Autowired
	private RuntimeService runtimeService;
	
	//http://localhost:8080/mfy-platform/bpm/start-process/123.do
    @RequestMapping( value="/start-process/{id}.do" )
    public String completeProcess(HttpServletRequest request,HttpServletResponse response,@PathVariable String id){
    	LOG.info("begin start-process:" + id );
    	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("oneUserTaskProcess");
    	LOG.info("start-process, process id:" + processInstance.getId());
        return "ok";
    }


    
}


