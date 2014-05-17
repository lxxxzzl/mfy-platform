package com.hcb.mc.factory;

public interface MatchingBean<T> {

    boolean matching(T factor);
    
}
