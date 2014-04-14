package com.mfy.log;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.qos.logback.classic.util.ContextInitializer;

public class LogContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("config file :"
                + sce.getServletContext().getInitParameter(ContextInitializer.CONFIG_FILE_PROPERTY));
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY,
                sce.getServletContext().getInitParameter(ContextInitializer.CONFIG_FILE_PROPERTY));
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.clearProperty(ContextInitializer.CONFIG_FILE_PROPERTY);
    }

}