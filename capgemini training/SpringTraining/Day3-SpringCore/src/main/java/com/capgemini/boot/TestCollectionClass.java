package com.capgemini.boot;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.pojo.CollectionDemo;

public class TestCollectionClass {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("collectionBeans.xml");
		
		CollectionDemo demo = (CollectionDemo) context.getBean("collDemo");
		
		System.out.println(demo);
		
		context.close();
	}

}
