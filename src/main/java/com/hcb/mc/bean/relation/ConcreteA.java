package com.hcb.mc.bean.relation;

import org.springframework.stereotype.Service;

@Service
public class ConcreteA extends AbstractBase {
//public class Concrete  {

	public String name = "ConcreteA";
	
	@Override
	public void prt() {
		System.out.println("ConcreteA prt");
	}

	public String whoami() {
		return "i am Concrete A";
	}
}
