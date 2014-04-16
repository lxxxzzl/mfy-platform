package com.mfy.util;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class MailUtility {

    @Async
    public void sendMail(String name) {

    	System.out.println("在做发送准备工作中");

        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        System.out.println("异步发送完毕");

    }
    
    @Async
    public Future<String> longOpt() {
        System.out.println("start longOpt..");
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end longOpt");
        return new AsyncResult("long opt ok");
    }
    
}