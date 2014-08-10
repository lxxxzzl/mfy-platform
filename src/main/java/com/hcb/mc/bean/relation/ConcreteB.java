package com.hcb.mc.bean.relation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Service
public class ConcreteB extends AbstractBase {

	private static Logger LOG = LoggerFactory.getLogger(ConcreteB.class);
	
	public String name = "ConcreteB";
	
	@Override
	public void prt() {
		LOG.info("ConcreteB prt");
	}

	public String whoami() {
		return "i am ConcreteB ";
	}
}
