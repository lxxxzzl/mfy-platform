package com.hcb.test.mutiinstance.delegate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetSubProcessVarCollecion {

	public List<String> getSubProcVarList() {
		List<String> list = new ArrayList<String>();
		list.add("var1");
		list.add("var2");
		System.out.println("-------------------GetSubProcessVarCollecion---------------");
		return list;
	}
	
}
