package com.hcb.mc.vo;

import java.util.Date;

public class User {
	private int id;
	private String name;
	private Date opTime;
	
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.getId() + " " + this.getName();
	}
}
