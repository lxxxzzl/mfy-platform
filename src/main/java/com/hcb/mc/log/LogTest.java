package com.hcb.mc.log;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcb.mc.vo.User;

public class LogTest {

	static Logger LOG = LoggerFactory.getLogger(LogTest.class);
	
	public static void main(String[] args) {
		 oom();
		LOG.info("aaaaa");
	}

    private static void oom(){
        Map<String, User> map = new HashMap<String, User>();
        Object[] array = new Object[10000000];
        //用来测试jvm内存溢出
        for(int i=0; i<10000000; i++){
            User p = new User();
            map.put(i+"rosen jiang", p);
            array[i]=p;
        }
        LOG.info("OK");
    }    
	     
	    
}
