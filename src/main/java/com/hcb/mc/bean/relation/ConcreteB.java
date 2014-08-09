package com.hcb.mc.bean.relation;

import org.springframework.stereotype.Service;

//@Service
public class ConcreteB extends AbstractBase {

	public String name = "ConcreteB";
	
	@Override
	public void prt() {
		System.out.println("ConcreteB prt");
	}

	public String whoami() {
		return "i am ConcreteB ";
	}
}
