package com.hcb.mc.bean;

import org.springframework.stereotype.Service;

@Service
public class MutliBeanA implements MutliBean {
    
    public boolean matching(String factor) {
        return factor != null && factor.equalsIgnoreCase("a");
    }

    public void methodA() {
        System.out.println("I am B");
    }

}
