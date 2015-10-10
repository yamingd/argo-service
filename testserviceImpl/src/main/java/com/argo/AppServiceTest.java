package com.argo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yamingd on 9/11/15.
 */
public class AppServiceTest {

    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("service-server.xml");
        Thread.sleep(1000 * 300);
    }

}
