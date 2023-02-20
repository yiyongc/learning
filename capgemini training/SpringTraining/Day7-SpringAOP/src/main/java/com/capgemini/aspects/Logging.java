package com.capgemini.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Logging {
	
	@Pointcut("execution (* com.capgemini.service.EmployeeServiceImpl.findEmployee(..))")
	public void myPointCut() {}
	
	@Before(value="myPointCut()")
	public void beforeMethod() {
		System.out.println("Before my method...");
	}
	
	@After(value="myPointCut()")
	public void afterMethod() {
		System.out.println("After my method...");
	}
	
	@AfterReturning(value="myPointCut()", returning="value")
	public void afterReturnMethod(Object value) {
		System.out.println("Object has been returned " + value);
	}
	
	@AfterThrowing(value="myPointCut()", throwing="ex")
	public void afterThrowingException(Exception ex) {
		System.out.println("Exception from method: "+ ex.getMessage());
	}
	
	@Around(value="myPointCut()")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("WASSUP");
		Object[] actualArgs = pjp.getArgs();
		for (Object o : actualArgs)
			System.out.println("Argument: " + o);
			
		actualArgs[0] = 2;
		Object ret =  pjp.proceed(actualArgs);
		
		System.out.println("after around");
		
		return ret;
	}
}
