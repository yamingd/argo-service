package com.argo.testcases;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yamingd on 9/11/15.
 */
public class AppServiceClientTest {

    public static void main(String[] args) throws Exception{

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("service-client.xml");

        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("service-all.xml");
    }

}
