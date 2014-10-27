package com.hcb.bpm.delegate;

import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OneJobDelegate implements JavaDelegate {

  private static Logger LOG = LoggerFactory.getLogger(OneJobDelegate.class);
	
  public void execute(DelegateExecution execution) {
	  
	  try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  
	  LOG.info("-------- OneJobDelegate ----------"); 
//	  if (true)
//		  throw new RuntimeException("Test interupt job hcb exception!");
  }
  
}
