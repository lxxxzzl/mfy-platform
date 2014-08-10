package com.hcb.mc.factory;

import java.util.ArrayList;
import java.util.Iterator;

public class FactoryArrayList<E extends MatchingBean<K>, K> extends ArrayList<E> implements FactoryList<E, K> {

    private static final long serialVersionUID = 5705342394882249201L;

    public FactoryArrayList() {
        super();
    }
    
    public FactoryArrayList(int size) {
        super(size);
    }
    
    public E getBean(K factor) {
        Iterator<E> itr = iterator();
        while(itr.hasNext()) {
            E beanMatch = itr.next();
            if(beanMatch.matching(factor)) {
                return beanMatch;
            }
        }
        return null;
    }
    
}
