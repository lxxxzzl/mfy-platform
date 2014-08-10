package com.hcb.mc.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hcb.mc.bean.relation.ConcreteB;

@Service
public class MutliBeanA implements MutliBean {
    
	private static Logger LOG = LoggerFactory.getLogger(MutliBeanA.class);
	
    public boolean matching(String factor) {
        return factor != null && factor.equalsIgnoreCase("a");
    }

    public void methodA() {
        LOG.info("I am B");
    }

}
