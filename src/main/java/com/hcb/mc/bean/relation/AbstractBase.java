package com.hcb.mc.bean.relation;

import org.springframework.stereotype.Service;

@Service
public abstract class AbstractBase {

	public String name = "AbstractBase";
	
	public abstract void prt();

	public String whoami() {
		return "i am AbstractBase ";
	}
}
