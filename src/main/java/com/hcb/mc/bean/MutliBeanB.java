package com.hcb.mc.bean;

import org.springframework.stereotype.Service;

@Service
public class MutliBeanB implements MutliBean {

    
    public boolean matching(String factor) {
        return factor != null && factor.equalsIgnoreCase("b");
    }

    public void methodA() {
        System.out.println("I am B");
    }

}
