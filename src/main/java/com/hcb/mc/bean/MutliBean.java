package com.hcb.mc.bean;

import org.springframework.stereotype.Service;

import com.hcb.factory.MatchingBean;


@Service
public interface MutliBean extends MatchingBean<String> {

    void methodA();
    
}
