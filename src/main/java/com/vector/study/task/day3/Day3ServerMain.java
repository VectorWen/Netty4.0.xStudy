package com.vector.study.task.day3;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * author: vector.huang
 * date: 2017/01/07 14:27
 */
public class Day3ServerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:TaskDay3ApplicationContext.xml");
        context.start();
    }

}
