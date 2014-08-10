package com.hcb.test.mutiinstance.delegate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetSubProcessVarCollecion {
	private static Logger LOG = LoggerFactory.getLogger(GetSubProcessVarCollecion.class);
	
	public List<String> getSubProcVarList() {
		List<String> list = new ArrayList<String>();
		list.add("var1");
		list.add("var2");
		LOG.info("-------------------GetSubProcessVarCollecion---------------");
		return list;
	}
	
}
