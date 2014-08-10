package com.hcb.mc.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MutliBeanB implements MutliBean {

	private static Logger LOG = LoggerFactory.getLogger(MutliBeanB.class);
	
    public boolean matching(String factor) {
        return factor != null && factor.equalsIgnoreCase("b");
    }

    public void methodA() {
        LOG.info("I am B");
    }

}
