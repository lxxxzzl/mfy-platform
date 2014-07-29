package com.hcb.test.mutiinstance.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GetSonCollecion {

	public List<String> getSonCollecion() {
		System.out.println("-------------------GetSonCollecion---------------");
		return Arrays.asList("big son","mid son","small son");
	}
	
}
