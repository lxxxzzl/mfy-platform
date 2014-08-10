package com.hcb.mc.bean.relation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ConcreteA extends AbstractBase {
	private static Logger LOG = LoggerFactory.getLogger(ConcreteA.class);
	
	public String name = "ConcreteA";
	
	@Override
	public void prt() {
		LOG.info("ConcreteA prt");
	}

	public String whoami() {
		return "i am Concrete A";
	}
}
