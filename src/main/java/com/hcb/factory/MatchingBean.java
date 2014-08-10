package com.hcb.factory;

public interface MatchingBean<T> {

    boolean matching(T factor);
    
}
