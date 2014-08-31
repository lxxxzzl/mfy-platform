package com.hcb.util;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class MailUtility {

	private static Logger LOG = LoggerFactory.getLogger(MailUtility.class);
	
    @Async
    public void sendMail(String name) {

    	LOG.info("在做发送准备工作中");

        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        LOG.info("异步发送完毕");

    }
    
    @Async
    public Future<String> longOpt() {
        LOG.info("start longOpt..");
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("end longOpt");
        return new AsyncResult<String>("long opt ok");
    }
    
}