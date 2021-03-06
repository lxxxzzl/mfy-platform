package com.hcb.factory;

import java.util.List;

public interface FactoryList<E extends MatchingBean<K>, K> extends List<E> {

    E getBean(K factor);
    
}
