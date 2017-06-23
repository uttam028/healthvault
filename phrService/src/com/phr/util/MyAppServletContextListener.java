package com.phr.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;


public class MyAppServletContextListener
               implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}

        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("ServletContextListener started");
		try {
	        DatabaseUtil.initializeDataConnection();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("exception while creating dbpool "+ e.getMessage());
		}
		
	}
}