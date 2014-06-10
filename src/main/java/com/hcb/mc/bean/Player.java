package com.hcb.mc.bean;

import org.springframework.stereotype.Service;

@Service
public class Player {
	

	private String name;

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
