package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//JUnit will invoke the class it references to run the tests in that class instead of the runner built into JUnit
@SpringBootTest
//used as an alternative to the standard spring-test
public class SareetaApplicationTests {


	@Test
	//test is a method contained in a class which is only used for testing

	//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
	public void contextLoads() {
	}

}
